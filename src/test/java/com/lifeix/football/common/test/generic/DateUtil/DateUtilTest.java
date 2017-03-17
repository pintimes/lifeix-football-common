package com.lifeix.football.common.test.generic.DateUtil;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.lifeix.football.common.util.DateUtil;

/**
 * @author xule
 * @version 2017年3月16日 上午11:24:42
 */
public class DateUtilTest {
	private Date now = new Date();
	private int year = now.getYear()+1900;
	private int month = now.getMonth()+1;
	private int date = now.getDate();
	private int hours = now.getHours();
	private int minutes = now.getMinutes();
	private int seconds = now.getSeconds();
	
	@Test
	public void toDateStrTest(){
		String dateStr = DateUtil.toDateStr(now);
		Assert.assertNotNull(dateStr);
		Assert.assertEquals(year+"-"+getTwoIntStr(month)+"-"+getTwoIntStr(date), dateStr);
	}
	
	@Test
	public void toTimeStrTest(){
		String timeStr = DateUtil.toTimeStr(now);
		Assert.assertNotNull(timeStr);
		Assert.assertEquals(year+"-"+getTwoIntStr(month)+"-"+getTwoIntStr(date)+" "
					+getTwoIntStr(hours)+":"+getTwoIntStr(minutes)+":"+getTwoIntStr(seconds), timeStr);
	}
	
	@Test
	public void toUTCStrTest(){
		String utcStr = DateUtil.toUTCStr(now);
		Date date2 = new Date(now.getTime()-28800000);
		Assert.assertNotNull(utcStr);
		Assert.assertTrue(utcStr.contains(date2.getYear()+1900+"-"+getTwoIntStr(date2.getMonth()+1)+"-"+getTwoIntStr(date2.getDate())+"T"
				+getTwoIntStr(date2.getHours())+":"+getTwoIntStr(date2.getMinutes())+":"+getTwoIntStr(date2.getSeconds())));
	}
	
	@Test
	public void engToDateTest(){
		Date date = DateUtil.engToDate(now.toString());
		Assert.assertNotNull(date);
		Assert.assertEquals(date.toString(), now.toString());
	}
	
	private String getTwoIntStr(int integer){
		if (integer<0||integer>99) {
			return null;
		}
		return integer<10?"0"+integer:""+integer;
	}
}
