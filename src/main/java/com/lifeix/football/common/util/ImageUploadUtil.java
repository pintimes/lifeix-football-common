package com.lifeix.football.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.lifeix.football.common.exception.BusinessException;
import com.lifeix.football.common.exception.IllegalparamException;
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
		for (int i = 0; i < 3; i++) {//上传的图片和原图大小不同时进行3次尝试，如果3次上传结果和原图都不一样大，则返回null
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
		}
		return null;
	}
	
	/**
	 * 图片上传方法2:<br/>增加图片地址有效性检查，如果图片存在重定向地址则替换图片地址为重定向地址；<br/>采用okhttp工具类发送请求；<br/>将图片保存在临时文件夹中后再上传；
	 * <br/>判断原图和新图大小是否相同，相同时认为上传成功，否则失败；<br/>图片上传失败时抛出异常
	 * @author xule
	 * @version 2017年2月3日 下午2:16:45
	 * @param 
	 * @return String
	 */
	public static String uploadImage2(String fileHost, String imageHost, String imagePrefix, String imgUrl) {
		if (StringUtils.isEmpty(imgUrl)) {
			throw new IllegalparamException("上传失败，图片路径为空！");
		}
		/**
		 * 图片有效性检查，有效时返回原图地址或重定向地址，无效时返回null
		 */
		String originImage=ImageUtil.availabilityCheck(imgUrl);
		if (StringUtils.isEmpty(originImage)) {
			throw new BusinessException("图片路径无效！图片地址:"+imgUrl);
		}
		/**
		 * 获得原图大小
		 */
		int originSize=ImageUtil.getImageSize(originImage);
		if (originSize==-1) {
			throw new BusinessException("获取图片大小失败，图片地址："+originImage);
		}
		try {
			/**
			 * 读取图片
			 */
			String filePath = ImageUtil.writeAndGetImagePath(originImage);
			if (StringUtils.isEmpty(filePath)) {
				throw new BusinessException("获取图片并写入临时文件失败，图片地址："+originImage);
			}
			
			/**
			 * 计算唯一的图片名
			 */
			String key = QiniuEtag.file(filePath)+ImageUtil.getSuffix(originImage);
			
			/**
			 * 判断是否需要加上图片名前缀
			 */
			if (!StringUtils.isEmpty(imagePrefix)) {//图片前缀不为空，加上前缀
				key=imagePrefix+key;
			}
			
			String newImage = imageHost+key;//生成图片完整路径
			/**
			 * 判断图片是否已经上传过
			 */
			if (OKHttpUtil.head(newImage)) {//图片存在，不需要重复上传
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
				throw new BusinessException("从文件服务获取图片上传token失败");
			}
			String uploadToken = uploadTokenJson.getString("uptoken");
			if (StringUtils.isEmpty(uploadToken)) {
				throw new BusinessException("图片上传token为空");
			}
			/**
			 * 上传图片
			 */
			Response res=null;
			res = new UploadManager().put(filePath, key, uploadToken);
			if (res==null) {
				throw new BusinessException("上传图片失败");
			}
			if (res.statusCode==200) {//图片上传成功
				int newSize=ImageUtil.getImageSize(newImage);
				if (newSize==-1) {
					throw new BusinessException("获取上传后的图片大小失败，图片地址："+newImage);
				}
				if (originSize==newSize) {//旧图和新图大小相同，认为图片上传完整，返回新图地址
					return newImage;
				}
			}
			throw new BusinessException("图片上传失败，"+res.error);
		} catch (Exception e) {
			throw new BusinessException("上传失败，"+e.getMessage());
		} 
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

}