package com.lifeix.football.common.util;

import javax.servlet.http.HttpServletRequest;

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
        if (StringUtils.isEmpty(groups)||!groups.contains("admin")) {
            throw new AuthorizationException();
        }
        //HttpServletRequest currentRequest = BaseApi.getCurrentRequest();
    	/**
    	 * 内网进入的认为是管理员，admin不通过网关传入
    	 * 从前置获得header from字段，如果有值则表示是从kong过来的即不是一个Admin
    	 */
    	//String from = currentRequest.getHeader("from");
    	//if (!StringUtils.isEmpty(from)) {
		//	throw new AuthorizationException();
		//}
    }
    
    
    /**
     * @name userAdminAuthorization
     * @description 管理员或用户授权，groups必须包含user或者admin才能通过授权
     * @author xule
     * @version 2016年8月24日 上午10:06:14
     * @param 
     * @return void
     * @throws
     */
    public static void userAdminAuthorization(String groups) {
        if (StringUtils.isEmpty(groups)||!(groups.contains("user")||groups.contains("admin"))) {
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
  /**
   * @name userAuthorization
   * @description 用户授权认证
   * @author xule
   * @version 2016年9月23日 下午2:11:29
   * @param 
   * @return void
   * @throws
   */
    public static void userAuthorization(String groups,String kongId,String userId) {
    	if (StringUtils.isEmpty(groups)) {
    		throw new AuthorizationException();
    	}
    	if (groups.contains("user")&&(kongId==null&&userId==null||userId.equals(kongId))) {
    		return;
    	}
    	throw new AuthorizationException();
    }
    
    /**
     * @name competitionUserAuthorization
     * @description 赛事系统用户授权认证
     * @author xule
     * @version 2016年9月23日 下午2:11:29
     * @param 
     * @return void
     * @throws
     */
    public static void competitionUserAuthorization(String groups) {
    	if (StringUtils.isEmpty(groups)||!groups.contains("competition_user")) {
    		throw new AuthorizationException();
    	}
    }
    
    /**
     * @name competitionUserAuthorization
     * @description 赛事系统用户授权认证
     * @author xule
     * @version 2016年9月23日 下午2:11:29
     * @param 
     * @return void
     * @throws
     */
    public static void competitionUserAuthorization(String groups,String kongId,String userId) {
    	if (StringUtils.isEmpty(groups)) {
    		throw new AuthorizationException();
    	}
    	if (groups.contains("competition_user")&&userId.equals(kongId)) {
    		return;
    	}
    	throw new AuthorizationException();
    }


	/**
	 * @name monitorAuthorization
	 * @description
	 * @author xule
	 * @version 2016年10月18日 上午10:09:59
	 * @param 
	 * @return void
	 * @throws 
	 */
	public static void monitorAuthorization(String groups) {
		if (StringUtils.isEmpty(groups)) {
    		throw new AuthorizationException();
    	}
    	if (groups.contains("monitor")) {
    		return;
    	}
    	throw new AuthorizationException();
	}

}
