package com.lifeix.football.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.lifeix.football.common.exception.AuthorizationException;

public class AuthorizationUtil {

	public static final String ROLE_VISITOR = "visitor";
	public static final String ROLE_USER = "user";
	public static final String ROLE_ADMIN = "admin";
	public static final String HEADER_FROM = "from";

	/**
	 * 管理员简单授权，只要groups中包含了admin字段即可通过
	 * 
	 * @description
	 * @author zengguangwei
	 * @version 2016年5月10日下午3:50:08
	 *
	 * @param groups
	 */
	@Deprecated
	public static void adminAuthorization(String groups) {
		if (StringUtils.isEmpty(groups) || !groups.contains(ROLE_ADMIN)) {
			throw new AuthorizationException();
		}
	}

	/**
	 * 管理员授权
	 * @description不是从网关中过来的认为是管理员
	 * @author zengguangwei
	 * @version 2016年5月10日下午3:50:08
	 *
	 * @param groups
	 */
	public static void adminAuth() {
		// 只要是内网即可通过 如果通过Kong过来的header中from=kong
		if (checkAdminAuth()) {
			return;
		}
		throw new AuthorizationException();
	}
	
	public static boolean checkAdminAuth() {
		HttpServletRequest currentRequest = BaseApi.getCurrentRequest();
		if (currentRequest==null) {
			return false;
		}
		// 只要是内网即可通过 如果通过Kong过来的header中from=kong
		String header = currentRequest.getHeader("from");
		if (!StringUtils.isEmpty(header)) {
			return false;
		}
		return true;
	}
	
	

	/**
	 * 
	 * @description
	 * @author zengguangwei 
	 * @version 2016年11月8日下午1:26:09
	 *
	 * @param groups
	 */
	public static void userAuthorization(String groups) {
		if (StringUtils.isEmpty(groups)) {
			throw new AuthorizationException();
		}
		if (!groups.contains(ROLE_USER)) {
			throw new AuthorizationException();
		}
	}

	/**
	 * @name userAdminAuthorization @description
	 * @author xule @version 2016年8月24日 上午10:06:14 @param @return void @throws
	 */
	@Deprecated
	public static void userAdminAuthorization(String groups) {
		if (StringUtils.isEmpty(groups) || !(groups.contains(ROLE_USER) || groups.contains(ROLE_ADMIN))) {
			throw new AuthorizationException();
		}
	}
	
	/**
	 * 用户或管理员验证，并返回用户或管理员角色字符串：user/admin
	 * @author xule
	 * @version 2017年1月16日 上午11:58:19
	 * @param 
	 * @return String
	 */
	public static String userAdminAuth(String groups) {
		if (StringUtils.isEmpty(groups)) {
			throw new AuthorizationException();
		}
		if (groups.contains(ROLE_USER)) {
			return ROLE_USER;
		}
		if (groups.contains(ROLE_ADMIN)) {
			return ROLE_ADMIN;
		}
		throw new AuthorizationException();
	}
	
	/**
	 * 用户权限校验
	 * @description
	 * @author zengguangwei 
	 * @version 2016年11月8日下午1:24:04
	 *
	 * @param groups
	 */
	public static void userAuth(String groups) {
		if (StringUtils.isEmpty(groups)) {
			throw new AuthorizationException();
		}
		if (!groups.contains(ROLE_USER)) {
			throw new AuthorizationException();
		}
	}
	
	/**
	 * 用户简单授权 groups中包含了admin字段即可通过 groups中包含了user字段kongId=userid即可通过
	 * 
	 * @description
	 * @author zengguangwei
	 * @version 2016年5月10日下午3:50:08
	 *
	 * @param groups
	 */
	public static void userAuth(String groups, String kongId, String userId) {
		if (StringUtils.isEmpty(groups)) {
			throw new AuthorizationException();
		}
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(kongId)) {
			throw new AuthorizationException();
		}
		if (groups.contains(ROLE_USER) && userId.equals(kongId)) {
			return;
		}
		throw new AuthorizationException();
	}

	/**
	 * 用户简单授权 groups中包含了admin字段即可通过 groups中包含了user字段kongId=userid即可通过
	 * 
	 * @description
	 * @author zengguangwei
	 * @version 2016年5月10日下午3:50:08
	 *
	 * @param groups
	 */
	@Deprecated
	public static void userAdminAuthorization(String groups, String kongId, String userId) {
		if (StringUtils.isEmpty(groups)) {
			throw new AuthorizationException();
		}
		if (groups.contains(ROLE_ADMIN)) {
			return;
		}
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(kongId)) {
			throw new AuthorizationException();
		}
		if (groups.contains(ROLE_USER) && userId.equals(kongId)) {
			return;
		}
		throw new AuthorizationException();
	}

}
