/**
 * 
 */
package com.lifeix.football.common.consts;

/**
 * @author xule
 */
public class UserConst {
	/*设备编号*/
	public static final int DEVICE_WEB=1;
	public static final int DEVICE_IOS=2;
	public static final int DEVICE_ANDROID=3;
	
	/*设备登录状态*/
	public static final int STATUS_ONLINE = 1;
	public static final int STATUS_OFFLINE = 0;

	/*性别,1:男，2:女*/
	public static final int GENDER_MALE = 1;
	public static final int GENDER_FEMALE = 2;
	
	/*注册用户信息来源*/
	public static final String USER_REGESTER_SOURCE_CF = "c-f";
	public static final String USER_REGESTER_SOURCE_CFCS = "competition";
	
	/*注册用户组*/
	public static final String GROUP_USER = "user";
	public static final String GROUP_COMPETITION_USER = "competition_user";
	
	/*密码最小位数*/
	public static final int PASSWORD_MIX_AMOUNT=8;
}
