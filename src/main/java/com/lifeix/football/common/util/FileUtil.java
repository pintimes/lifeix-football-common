package com.lifeix.football.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import org.springframework.util.StringUtils;

import com.lifeix.football.common.exception.BusinessException;
import com.lifeix.football.common.exception.IllegalparamException;

public class FileUtil {
	/**
	 * 
	 * @param filepath   xx.text
	 * @return
	 */
	public static String readFileContent(String filepath) {
		try {
			InputStream is = new FileInputStream(filepath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line).append("\n");
//				stringBuilder.append(line);
			}
			br.close();
			return stringBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param filename   xx.text
	 * @return
	 */
	public static String readFileFromResource(String filename) {
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line);
			}
			br.close();
			return stringBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得文件的名称
	 * @param filename
	 * @return
	 */
	public static String getFileName(File file){
		if (file.isDirectory()) {
			return file.getName();
		}
		String name = file.getName();
		return name.substring(0,name.indexOf("."));
	}

	public static void writeContent(String filepath, String content) {
		try {
			File file = new File(filepath);
			file.delete();
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeUTF(content);
			dos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeContent(String filepath, byte[] datas) {
		try {
			File file = new File(filepath);
			file.delete();
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(datas);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从文件链接获取文件并写入临时文件中
	 * @author xule
	 * @version 2017年2月3日 下午5:19:41
	 * @param 
	 * @return void
	 */
	public static String writeTempFile(String fileUrl){
		if (StringUtils.isEmpty(fileUrl)) {
			throw new IllegalparamException("文件链接为空，写入文件失败");
		}
		String tempDir = System.getProperty("java.io.tmpdir");
		String filepath = tempDir +UUID.randomUUID().toString();
		return writeFile(fileUrl, filepath);
	}
	
    /**
     * 从文件链接获取文件并写入本地文件中
     * @author xule
     * @version 2017年2月3日 下午5:23:13
     * @param 
     * @return String
     */
    public static String writeFile(String fileUrl, String filepath){
        InputStream is = null;
        FileOutputStream fos = null;
        try {
        	URL url = new URL(fileUrl);
            //connect
            URLConnection urlConn = url.openConnection();
            //get inputstream from connection
            is = urlConn.getInputStream();               
            fos = new FileOutputStream(filepath);

            // 4KB buffer
            byte[] buffer = new byte[4096]; 
            int length;

            // read from source and write into local file
            while ((length = is.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            return filepath;
        }catch(Exception e){
        }finally {
        	if (is != null) {
        		try {
        			is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            if (fos != null) {
                try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
        return null;
    }
	
    /**
     * 根据文件路径判断文件是否存在
     * @author xule
     * @version 2017年2月3日 下午5:22:18
     * @param 
     * @return boolean
     */
	public static boolean fileExists(String filepath){
		File file = new File(filepath);
		return file.exists();
	}
	
}
