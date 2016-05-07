package com.lifeix.football.common.util;

import java.util.Map;

import org.springframework.util.StringUtils;

import com.lifeix.football.common.util.EnvironmentUtil;
/**
 * 环境工具
 * @author zengguangwei
 *
 */
public class ProfileUtil {

    /**
     * 
     * @param appprofile 比如 ENV_PROFILE_项目名
     * @return
     */
    public static String[] getProfiles(String appprofile) {
        String os = EnvironmentUtil.getOS();
        /**
         * 开发环境
         */
        if (os.indexOf("Windows") != -1) {
            return new String[] { "common", "system","dev" };
        }
        /**
         * 测试，灰度,生产等 直接从环境变量中获得 所以容器启动的时候需要将设置环境变量 读取系统的环境变量ENV_PROFILE_项目名 如果是测试环境返回qa
         * 如果是沙箱环境返回sand 如果是灰度环境返回gray 如果是线上环境返回production
         */
        Map<String, String> sysEnvs = EnvironmentUtil.getSysEnvs();
        String envorment = sysEnvs.get(appprofile);
        if (StringUtils.isEmpty(envorment)) {
            envorment = "qa";
        }
        return new String[] { "common", "system",envorment };
    }
}
