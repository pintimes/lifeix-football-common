package com.lifeix.football.common.util;

import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.lifeix.football.common.exception.BusinessException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;

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
			logger.error("上传图片参数缺失！");
			return null;
		}
		try {
			/**
			 * 读取图片
			 */
			byte[] b = ImageUtil.readData(imgUrl);
			
			/**
			 * 计算唯一的图片名
			 */
			String key=ImageUtil.calcETag(b)+ImageUtil.getSuffix(imgUrl);

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
				return newImage;
			}
			return null;
		}catch (Exception e) {
			logger.error("图片上传失败  ",e.getMessage());
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
			String url=fileHost+"/football/file/token/upload?key=admin&file_type=1&file_name="+imgName;
			HttpGet http = new HttpGet(url);
			http.setHeader("Content-Type", "application/json");
			String result = HttpUtil.sendHttp(http);
			if (StringUtils.isEmpty(result)) {
				return null;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}