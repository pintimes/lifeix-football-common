package com.lifeix.football.common.util;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class IpUtil {

    /**
     * 获取本机的IP
     * 
     * @return Ip地址
     */
    public static String getLocalHostIP() {
        String ip;
        try {
            /** 返回本地主机。 */
            InetAddress addr = InetAddress.getLocalHost();
            /** 返回 IP 地址字符串（以文本表现形式） */
            ip = addr.getHostAddress();
        } catch (Exception ex) {
            ip = "";
        }

        return ip;
    }

    public static String getIpAddr(HttpServletRequest request) {
    	if (request==null) {
			return null;
		}
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
            String[] ips = ip.split(",");
            if (ips.length > 0) {
                ip = ips[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    public static String getIpAddr2(HttpServletRequest request) {
    	if (request==null) {
			return null;
		}
        String ip = request.getHeader("x-forwarded-for");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            String[] ips = ip.split(",");
            if (ips.length > 0) {
                ip = ips[0];
            }
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
