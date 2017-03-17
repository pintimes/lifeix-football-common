package com.lifeix.football.common.test.generic.AuthorizationUtil;

import org.junit.Assert;
import org.junit.Test;
import com.lifeix.football.common.util.AuthorizationUtil;

/**
 * @author xule
 * @version 2017年3月17日 上午10:02:30
 */
public class AuthorizationUtilTest {
	@Test
	public void adminAuthorizationTest(){
		try {
			AuthorizationUtil.adminAuthorization(AuthorizationUtil.ROLE_ADMIN);
		} catch (Exception e) {
			Assert.fail();
		}
		try {
			AuthorizationUtil.adminAuthorization(AuthorizationUtil.ROLE_VISITOR);
			Assert.fail();
		} catch (Exception e) {
		}
		try {
			AuthorizationUtil.adminAuthorization(AuthorizationUtil.ROLE_USER);
			Assert.fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void userAuthorizationTest(){
		try {
			AuthorizationUtil.userAuthorization(AuthorizationUtil.ROLE_USER);
		} catch (Exception e) {
			Assert.fail();
		}
		try {
			AuthorizationUtil.userAuthorization(AuthorizationUtil.ROLE_VISITOR);
			Assert.fail();
		} catch (Exception e) {
		}
		try {
			AuthorizationUtil.userAuthorization(AuthorizationUtil.ROLE_ADMIN);
			Assert.fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void userAuthTest(){
		try {
			AuthorizationUtil.userAuth(AuthorizationUtil.ROLE_USER);
		} catch (Exception e) {
			Assert.fail();
		}
		try {
			AuthorizationUtil.userAuth(AuthorizationUtil.ROLE_VISITOR);
			Assert.fail();
		} catch (Exception e) {
		}
		try {
			AuthorizationUtil.userAuth(AuthorizationUtil.ROLE_ADMIN);
			Assert.fail();
		} catch (Exception e) {
		}
	}

	@Test
	public void userAuth2Test(){
		/**
		 * 角色为用户，id相等，通过
		 */
		try {
			AuthorizationUtil.userAuth(AuthorizationUtil.ROLE_USER,"id","id");
		} catch (Exception e) {
			Assert.fail();
		}
		/**
		 * 角色为用户，id不相等，抛出异常
		 */
		try {
			AuthorizationUtil.userAuth(AuthorizationUtil.ROLE_USER,"id","id2");
			Assert.fail();
		} catch (Exception e) {
		}
		/**
		 * 其他角色，无论id是否相同，均抛出异常
		 */
		try {
			AuthorizationUtil.userAuth(AuthorizationUtil.ROLE_ADMIN,"id","id");
			Assert.fail();
		} catch (Exception e) {
		}
		try {
			AuthorizationUtil.userAuth(AuthorizationUtil.ROLE_ADMIN,"id","id2");
			Assert.fail();
		} catch (Exception e) {
		}
		try {
			AuthorizationUtil.userAuth(AuthorizationUtil.ROLE_VISITOR,"id","id");
			Assert.fail();
		} catch (Exception e) {
		}
		try {
			AuthorizationUtil.userAuth(AuthorizationUtil.ROLE_VISITOR,"id","id2");
			Assert.fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void userAdminAuthorizationTest(){
		/**
		 * 角色为用户，id相等，通过
		 */
		try {
			AuthorizationUtil.userAdminAuthorization(AuthorizationUtil.ROLE_USER,"id","id");
		} catch (Exception e) {
			Assert.fail();
		}
		/**
		 * 角色为用户，id不相等，抛出异常
		 */
		try {
			AuthorizationUtil.userAdminAuthorization(AuthorizationUtil.ROLE_USER,"id","id2");
			Assert.fail();
		} catch (Exception e) {
		}
		
		/**
		 * 角色为管理员，通过
		 */
		try {
			AuthorizationUtil.userAdminAuthorization(AuthorizationUtil.ROLE_ADMIN,"id","id");
		} catch (Exception e) {
			Assert.fail();
		}
		try {
			AuthorizationUtil.userAdminAuthorization(AuthorizationUtil.ROLE_ADMIN,"id","id2");
		} catch (Exception e) {
			Assert.fail();
		}
		/**
		 * 角色为管理员，抛出异常
		 */
		try {
			AuthorizationUtil.userAdminAuthorization(AuthorizationUtil.ROLE_VISITOR,"id","id");
			Assert.fail();
		} catch (Exception e) {
		}
		try {
			AuthorizationUtil.userAdminAuthorization(AuthorizationUtil.ROLE_VISITOR,"id","id2");
			Assert.fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void isUserTest(){
		Assert.assertTrue(AuthorizationUtil.isUser(AuthorizationUtil.ROLE_USER));
		Assert.assertFalse(AuthorizationUtil.isUser(AuthorizationUtil.ROLE_VISITOR));
		Assert.assertFalse(AuthorizationUtil.isUser(AuthorizationUtil.ROLE_ADMIN));
	}
}
