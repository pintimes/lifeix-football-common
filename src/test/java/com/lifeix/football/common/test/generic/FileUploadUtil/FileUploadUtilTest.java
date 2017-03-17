package com.lifeix.football.common.test.generic.FileUploadUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.lifeix.football.common.util.FileUploadUtil;
import com.lifeix.football.common.util.OKHttpUtil;

/**
 * @author xule
 * @version 2017年3月17日 上午10:27:18
 */
public class FileUploadUtilTest {
	@Test
	public void fileUploadTest(){
		//测试图片
		String []imgs={
				"https://resources.c-f.com/wemedia/images/o_1b5k3acuq1br67qmvlr1ssnkolr.jpeg", //普通图片（53K）
				"http://photo.l99.com/bigger/047/1486099556931_tv0hri.jpg",//带重定向地址的图片（206K）
				"http://photo.l99.com/bigger/d17/1487675183262_8eu9ib.gif",//gif大图（3.5M）
				"https://resources.c-f.com/wemedia/images/838319f6ccbbdf793c83f9bf7da0e381.jpeg?imageView/1/w/310/h/233",//带参图片地址（18K）
				"https://oi5mmhyk8.qnssl.com/412/1486949033576_191k4j.png!common",//带参图片地址（113K）
				"https://resources.c-f.com/testfiles/test",//无后缀图片地址（114K）
		};
		List<String> imageList=new ArrayList<>(Arrays.asList(imgs)).parallelStream().map(img->uploadFile(img, FileUploadUtil.FILETYPE_IMAGE)).collect(Collectors.toList());
		Assert.assertEquals(imgs.length, imageList.size());
		
		//测试音频
		String []audios={
				"http://resources.c-f.com/elearning/materials/EN%20dyamic%20yo-yo.mp3"//普通音频（40M）
		};
		List<String> audioList=new ArrayList<>(Arrays.asList(audios)).parallelStream().map(audio->uploadFile(audio, FileUploadUtil.FILETYPE_AUDIO)).collect(Collectors.toList());
		Assert.assertEquals(audios.length, audioList.size());
		
		//测试视频
		String []videos={
				"https://resources.c-f.com/files/o_1b5pp8ui61jdhav714q61fjn8g22t.mp4",//普通视频（24M）
				"http://qn.video.l99.com/997838_1488767589.mp4"
		};
		List<String> videoList=new ArrayList<>(Arrays.asList(videos)).parallelStream().map(video->uploadFile(video, FileUploadUtil.FILETYPE_VIDEO)).collect(Collectors.toList());
		Assert.assertEquals(videos.length, videoList.size());
		
		//测试大文件 100M
		String []bigFiles={
				"https://resources.c-f.com/elearning/materials/o_1b3d3ogq1m951u10122fo881p5e6h.pdf",//pdf大文件（200M）
		};
		List<String> fileList=new ArrayList<>(Arrays.asList(bigFiles)).parallelStream().map(file->uploadFile(file, FileUploadUtil.FILETYPE_ALL)).collect(Collectors.toList());
		Assert.assertEquals(bigFiles.length, fileList.size());
		
	}
	
	private String uploadFile(String fileUrl,String fileType){
		Assert.assertTrue(OKHttpUtil.head(fileUrl));
		String fileHost="http://54.223.127.33:8300";
		String bucket="https://resources.c-f.com/";
		String filePrefix="filesfortest/";
		try {
			String result=null;
			switch (fileType) {
				case FileUploadUtil.FILETYPE_ALL :
					result=FileUploadUtil.genericUpload(fileHost, bucket, filePrefix, fileUrl);
					break;
				case FileUploadUtil.FILETYPE_IMAGE :
					result=FileUploadUtil.imageUpload(fileHost, bucket, filePrefix, fileUrl);
					break;
				case FileUploadUtil.FILETYPE_VIDEO :
					result=FileUploadUtil.videoUpload(fileHost, bucket, filePrefix, fileUrl);
					break;
				case FileUploadUtil.FILETYPE_AUDIO :
					result=FileUploadUtil.audioUpload(fileHost, bucket, filePrefix, fileUrl);
					break;
				default :
					break;
			}
			System.out.println("---- "+result);
			Assert.assertTrue(OKHttpUtil.head(result));
			return result;
		} catch (Exception e) {
			Assert.fail();
		}
		return null;
	}
}
