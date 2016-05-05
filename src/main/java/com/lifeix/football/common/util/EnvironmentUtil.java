package com.lifeix.football.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentUtil {

    /**
     * 获取操作系统的名称
     * 
     * @return
     */
    public static String getOS() {
        return System.getProperty("os.name");
    }

    /**
     * @param os
     * @return
     */
    public static boolean isOS(String os) {
        String osName = System.getProperty("os.name");
        if (osName == null || os == null) {
            return false;
        }
        if (osName.equalsIgnoreCase(os)) {
            return true;
        }
        return false;
    }

    /**
     * 获取系统的环境变量
     * 
     * @return
     */
    public static Map<String, String> getSysEnvs() {
        return System.getenv();
    }

    /**
     * 获取系统的环境变量
     * 
     * @return
     */
    public static String getSysSearchLibPath() {
        return System.getProperty("java.library.path");
    }

    /**
     * CMD窗口: 获取windows环境变量
     * 
     * @param map
     */
    public static Map<String, String> getWindowsEnvs() {
        Map<String, String> envs = new HashMap<String, String>();
        if (("windows xp").equals(System.getProperty("os.name").toLowerCase())) {
            BufferedReader br = null;
            try {
                // 发送系统命令
                Process pro = Runtime.getRuntime().exec("cmd /c set");
                // 返回输入流
                br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
                String line = null;
                while ((line = br.readLine()) != null) {
                    String envName = line.substring(0, line.indexOf("="));
                    if (envName != null) {
                        String envValue = line.substring(line.indexOf("=") + 1);
                        envs.put(envName, envValue);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    br = null;
                }
            }
        }
        return envs;
    }

}