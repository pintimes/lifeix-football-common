package com.lifeix.football.common.test.generic.PasswordHasher;

import org.junit.Test;

import com.lifeix.football.common.util.PasswordHasher;

/**
 * @author xule
 * @version 2017年3月16日 下午6:06:07
 */
public class PasswordHasherTest {
	@Test
	public void createHashTest(){
		PasswordHasher passwordHasher=new PasswordHasher();
		System.out.println(passwordHasher.createHash("1"));
		System.out.println(passwordHasher.createHash("1"));
		System.out.println(passwordHasher.createHash("1"));
		System.out.println(passwordHasher.createHash("1"));
		System.out.println(passwordHasher.createHash("1"));
	}
}
