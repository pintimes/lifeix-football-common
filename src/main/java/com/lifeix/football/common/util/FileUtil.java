package com.lifeix.football.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.springframework.util.StringUtils;

public class FileUtil {
    /**
     * 从本地文件中读取内容
     * 
     * @param filepath 文件绝对路径，如：C:\\Users\\dd\\Desktop\\temp.txt
     * @return
     */
    public static String readFileContent(String filepath) {
        try {
            return readFileFromStream(new FileInputStream(filepath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从工程资源文件中读取内容
     * 
     * @param filename resources文件夹下的文件名，如：application-system.properties
     * @return
     */
    public static String readFileFromResource(String filename) {
        try {
            return readFileFromStream(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从流中读取文件内容，读取完毕后流将关闭，调用方无需再重复关闭流
     * 
     * @author xule
     * @version 2017年3月17日 下午5:02:56
     * @param
     * @return String
     * @throws Exception
     */
    public static String readFileFromStream(InputStream is) throws Exception {
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        try {
            inputStreamReader = new InputStreamReader(is);
            br = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 获得文件的名称
     * 
     * @param filename
     * @return
     */
    public static String getFileName(File file) {
        if (file.isDirectory()) {
            return file.getName();
        }
        String name = file.getName();
        return name.substring(0, name.indexOf("."));
    }

    /**
     * 写入文件
     */
    public static void writeContent(String filepath, String content) {
        if (StringUtils.isEmpty(content)) {
            return;
        }
        writeContent(filepath, content.getBytes());
    }

    /**
     * 写入文件
     */
    public static void writeContent(String filepath, byte[] datas) {
        writeContent(new File(filepath), datas);
    }

    /**
     * 写入文件
     */
    public static void writeContent(File file, byte[] datas) {
        FileOutputStream fos = null;
        try {
            file.delete();
            file.createNewFile();
            fos = new FileOutputStream(file);
            fos.write(datas);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeContent(File file, String content) {
        if (StringUtils.isEmpty(content)) {
            return;
        }
        writeContent(file, content.getBytes());
    }

}
