package com.homolo.homolo.test;

import com.homolo.homolo.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Date;

/**
 * @Author: ZH
 * @Description: lang3包工具类测试.
 * @Date: 19-9-26 下午1:36
 */
public class DateUtileTest {


	@Test
	public void test() {
//		commons-lang3包DateFormatUtils类测试
		String str = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
		System.out.println(str);
		String str1 = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd");
		System.out.println(str1);
	}
	@Test
	public void test1() {
//		commons-lang3包DateUtils类测试
		String str = DateFormatUtils.format(DateUtils.addMonths(new Date(), 1), "yyyy-MM-dd hh:mm:ss");
		System.out.println(str);
		String str2 = DateFormatUtils.format(DateUtils.addHours(new Date(), 1), "yyyy-MM-dd hh:mm:ss");
		System.out.println(str2);
	}
	@Test
	public void  test2() {
		System.out.println("测试随机数类");
		System.out.println(RandomUtils.nextInt());
		System.out.println(RandomUtils.nextBoolean());
		System.out.println(RandomUtils.nextInt(1,4));
		System.out.println(RandomStringUtils.random(4));
		System.out.println(RandomStringUtils.random(4, "w"));
		System.out.println(RandomStringUtils.randomAlphabetic(4));
		System.out.println(RandomStringUtils.randomNumeric(6));
	}

	@Test
	public void test3 () {
		String str = DateFormatUtils.format(DateUtil.getYearEndDate(new Date()), "yyyy-MM-dd hh:mm:ss");
		System.out.println(str);
	}
}
