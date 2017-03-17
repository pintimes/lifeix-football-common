package com.lifeix.football.common.test.generic.FileUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;

import com.lifeix.football.common.util.FileUtil;

/**
 * @author xule
 * @version 2017年3月16日 下午2:59:27
 */
public class FileUtilTest {
	@Test
	public void readFileContentTest(){
		String testString="filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test"+
						"filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test filtutil test";
		String filepath="C:\\Users\\dd\\Desktop\\temp.txt";
		String readFileContent = FileUtil.readFileContent(filepath);
		System.out.println(readFileContent);
		System.out.println(testString);
		System.out.println(readFileContent.length());
		System.out.println(testString.length());
		Assert.assertNotNull(readFileContent);
		Assert.assertEquals(testString, readFileContent);
	}
	
	@Test
	public void readFileFromResourceTest() throws UnsupportedEncodingException{
		String testString="################################################All Environment###################################################################System#####################management.address=127.0.0.1management.port=8081management.context-path=/managespring.main.show_banner=falseserver.port=8080server.session-timeout=1000";
		String readFileFromResource = FileUtil.readFileFromResource("application-system.properties");
		Assert.assertNotNull(readFileFromResource);
		Assert.assertEquals(testString, readFileFromResource.substring(0, testString.length()));
	}
	
	@Test
	public void getFileNameTest(){
		File file=new File("C:\\Users\\dd\\Desktop\\temp.txt");
		String fileName = FileUtil.getFileName(file);
		Assert.assertNotNull(fileName);
		Assert.assertEquals("temp", fileName);
	}
	
	@Test
	public void writeContentTest(){
		FileUtil.writeContent("C:\\Users\\dd\\Desktop\\temp2.txt", "content");
	}
	
	@Test
	public void writeContent2Test(){
		FileUtil.writeContent("C:\\Users\\dd\\Desktop\\temp3.txt", "content2".getBytes());
	}
	
}
