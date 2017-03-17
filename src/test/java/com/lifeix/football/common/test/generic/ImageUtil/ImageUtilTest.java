/**
 * @author xule
 * @version 2017年3月16日 下午4:36:02
 */
package com.lifeix.football.common.test.generic.ImageUtil;

import org.junit.Assert;
import org.junit.Test;

import com.lifeix.football.common.util.ImageUtil;

/**
 * @author xule
 * @version 2017年3月16日 下午4:36:02
 */
public class ImageUtilTest {
	@Test
	public void getSuffixTest(){
		String imgUrl = "http://www.xm.com/xx.jpg";
		String suffix = ImageUtil.getSuffix(imgUrl);
		Assert.assertEquals(".jpg", suffix);
		
		String imgUrl1 = "http://www.xm.com/xx.jpg?imageView=1.2";
		String suffix1 = ImageUtil.getSuffix(imgUrl1);
		Assert.assertEquals(".jpg", suffix1);
		
		String imgUrl2 = "http://www.xm.com/xx.giff?imageView=1";
		String suffix2 = ImageUtil.getSuffix(imgUrl2);
		Assert.assertEquals(".jpg", suffix2);
		
		String imgUrl3 = "http://www.xm.com/xx";
		String suffix3 = ImageUtil.getSuffix(imgUrl3);
		Assert.assertEquals(".jpg", suffix3);
		
		String imgUrl4 = "http://www.xm.com/xx.jpeg!bigger";
		String suffix4 = ImageUtil.getSuffix(imgUrl4);
		Assert.assertEquals(".jpeg", suffix4);
		
		String imgUrl5 = "http://www.xm.com/xx.jpeg?q=1!bigger";
		String suffix5 = ImageUtil.getSuffix(imgUrl5);
		Assert.assertEquals(".jpeg", suffix5);
		
		String imgUrl6 = "http://www.xm.com/xx.gif!bigger?p=a.jpeg";
		String suffix6 = ImageUtil.getSuffix(imgUrl6);
		Assert.assertEquals(".gif", suffix6);
		
		String imgUrl7 = "http://www.jpg.com/xx.gif!bigger?p=a.jpeg";
		String suffix7 = ImageUtil.getSuffix(imgUrl7);
		Assert.assertEquals(".gif", suffix7);
	}
	
	@Test
	public void readDataTest(){
		byte[] readData = ImageUtil.readData("https://resources.c-f.com/wemedia/images/FtY-34Fo8rXsOalFoloyWU4_7PMK.jpg?imageView/1/w/310/h/233");
		Assert.assertEquals(18210, readData.length);
	}
}
