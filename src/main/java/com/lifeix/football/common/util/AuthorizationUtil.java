package com.lifeix.football.common.util;

import org.springframework.util.StringUtils;

import com.lifeix.football.common.exception.AuthorizationException;

public class AuthorizationUtil {

    /**
     * 管理员简单授权，只要groups中包含了admin字段即可通过
     * 
     * @description
     * @author zengguangwei
     * @version 2016年5月10日下午3:50:08
     *
     * @param groups
     */
    public static void adminAuthorization(String groups) {
        if (StringUtils.isEmpty(groups)) {
            throw new AuthorizationException();
        }
        if (!groups.contains("admin")) {
            throw new AuthorizationException();
        }
    }
    
    /**
     * 用户简单授权
     * groups中包含了admin字段即可通过
     * groups中包含了user字段kongId=userid即可通过
     * 
     * @description
     * @author zengguangwei
     * @version 2016年5月10日下午3:50:08
     *
     * @param groups
     */
    public static void userAdminAuthorization(String groups,String kongId,String userId) {
        if (StringUtils.isEmpty(groups)) {
            throw new AuthorizationException();
        }
        if (groups.contains("admin")) {
        	return;
        }
        if (groups.contains("user")&&userId.equals(kongId)) {
            return;
        }
        throw new AuthorizationException();
    }

}
