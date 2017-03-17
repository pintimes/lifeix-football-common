package com.lifeix.football.common.test.generic.OKHttpUtil;

import org.junit.Assert;
import org.junit.Test;

import com.lifeix.football.common.util.JSONUtils;
import com.lifeix.football.common.util.OKHttpUtil;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

/**
 * @author xule
 * @version 2017年3月17日 上午10:32:03
 */
public class OKHttpUtilTest {
	private final String image1="https://resources.c-f.com/wemedia/images/o_1bbcvdjp31ci8gn921a4jvb2r.jpeg";//有效图片地址
	private final String image2="https://resources.c-f.com/wemedia/images/o_1bbcvdjp31ci8gn921a4jvb2r-.jpeg";//无效图片地址
	
	@Test
	public void headTest(){
		Assert.assertTrue(OKHttpUtil.head(image1));
		Assert.assertFalse(OKHttpUtil.head(image2));
	}
	
	@Test
	public void getTest(){
		/**
		 * 有效图片测试
		 */
		ResponseBody body=null;
		try {
			Response response = OKHttpUtil.get(image1, null);
			Assert.assertTrue(response.isSuccessful());
		} catch (Exception e) {
			System.out.println("OKHttpUtilTest fail! message: "+e.getMessage());
			Assert.fail();
		} finally {
			if (body!=null) {
				try {
					body.close();
				} catch (Exception e2) {
					Assert.fail();
					System.out.println("OKHttpUtilTest.postTest fail! message: "+e2.getMessage());
				}
			}
		}
		/**
		 * 无效图片测试
		 */
		ResponseBody body2=null;
		try {
			Response response = OKHttpUtil.get(image2, null);
			body2=response.body();
			Assert.assertFalse(response.isSuccessful());
		} catch (Exception e) {
			System.out.println("OKHttpUtilTest.getTest fail! message: "+e.getMessage());
			Assert.fail();
		} finally {
			if (body2!=null) {
				try {
					body2.close();
				} catch (Exception e2) {
					Assert.fail();
					System.out.println("OKHttpUtilTest.postTest fail! message: "+e2.getMessage());
				}
			}
		}
	}
	
	@Test
	public void postTest(){
		String url="http://54.223.127.33:8000/football/wemedia/posts?key=admin";
		Post post=new Post();
		post.setTitle("测试"+System.currentTimeMillis());
		post.setImage(image1);
		post.setContent("content");
		post.setSource("中国足球网");
		Author author=new Author();
		author.setName("xl");
		post.setAuthor(author);
		ResponseBody body=null;
		try {
			Response response = OKHttpUtil.post(url, null, post);
			Assert.assertNotNull(response);
			Assert.assertTrue(response.isSuccessful());
			body = response.body();
			byte[] bytes = response.body().bytes();
			String string = new String(bytes);
			Post post2 = JSONUtils.json2pojo(string, Post.class);
			Assert.assertNotNull(post2);
			Assert.assertNotNull(post2.getId());
		} catch (Exception e) {
			Assert.fail();
			System.out.println("OKHttpUtilTest.postTest fail! message: "+e.getMessage());
		} finally {
			if (body!=null) {
				try {
					body.close();
				} catch (Exception e2) {
					Assert.fail();
					System.out.println("OKHttpUtilTest.postTest fail! message: "+e2.getMessage());
				}
			}
		}
	}
	
}
