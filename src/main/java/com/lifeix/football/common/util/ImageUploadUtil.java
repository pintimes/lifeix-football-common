package com.lifeix.football.common.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.lifeix.football.common.exception.BusinessException;
import com.lifeix.football.common.exception.IllegalparamException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.squareup.okhttp.ResponseBody;

/**
 * @author xule
 */
public class ImageUploadUtil {
	private static Logger logger = LoggerFactory.getLogger(ImageUploadUtil.class);

	/**
	 * @name uploadImg
	 * @description 上传图片到七牛云
	 * @author xule
	 * @version 2016年8月3日 上午11:15:01
	 * @param 
	 * @return String 上传到七牛云的图片名（不是图片完整路径，不包含图片主机地址）
	 * @throws
	 * 
	 * 	app.imageHost=http://s.files.c-f.com/
		app.imagePrefix=wemedia/images/
	 */
	public static String uploadImage(String fileHost, String imageHost, String imagePrefix, String imgUrl) {
		if (StringUtils.isEmpty(imgUrl)) {
			throw new IllegalparamException("上传失败，图片地址为空！");
		}
		try {
			/**
			 * 读取图片
			 */
			byte[] b = ImageUtil.readData(imgUrl);
			if (b==null) {
				throw new BusinessException("读取线上图片失败");
			}
			/**
			 * 计算唯一的图片名
			 */
			String key=QiniuEtag.data(b)+ImageUtil.getSuffix(imgUrl);
			/**
			 * 判断是否需要加上图片名前缀
			 */
			if (!StringUtils.isEmpty(imagePrefix)) {//图片前缀不为空，加上前缀
				key=imagePrefix+key;
			}
			String newImage = imageHost+key;//生成图片完整路径
			if (HttpUtil.sendHead(newImage)) {//图片存在，不需要重复上传
				return newImage;
			}
			/**
			 * 获得图片上传token
			 */
			String imgName=key;
			String token = getUploadToken(fileHost,imgName);
			if (StringUtils.isEmpty(token)) {
				throw new BusinessException("图片上传token为空");
			}
			JSONObject uploadTokenJson= JSONObject.parseObject(token);//获得token
			if (uploadTokenJson==null||uploadTokenJson.isEmpty()) {
				throw new BusinessException("图片上传token为空");
			}
			String uploadToken = uploadTokenJson.getString("uptoken");
			/**
			 * 上传图片
			 */
			Response res = new UploadManager().put(b,key,uploadToken);//上传图片
			if (res.statusCode==200) {//图片上传成功
				byte[] b2 = ImageUtil.readData(newImage);//读取新图片
				if (b.length==b2.length) {//旧图和新图大小相同，认为图片上传完整，返回新图地址
					return newImage;
				}
			}
		} catch (Exception e) {
			logger.error("图片上传失败  "+e.getClass()+" "+e.getMessage()+" imgUrl="+imgUrl);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @name getToken
	 * @description
	 * @author xule
	 * @version 2016年8月2日 上午9:59:22
	 * @param 
	 * @return String
	 * @throws 
	 */
	private static String getUploadToken(String fileHost,String imgName) {
		try {
			String url=fileHost+"/football/file/token/upload?key=visitor&file_type=1&file_name="+imgName;
			String result = HttpUtil.sendGet(url);
			if (StringUtils.isEmpty(result)) {
				return null;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		com.squareup.okhttp.Response response = null;
		try {
			response = OKHttpUtil.get("http://photo.l99.com/common/645/1477359619752_uh6bn0.gif", null);
		} catch (Exception e) {
		}
		System.out.println(response);
		System.out.println(response.code());
		try {
			System.out.println(response.body().bytes().length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}