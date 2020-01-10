package com.homolo.homolo.test;

import com.homolo.homolo.utils.EncryptionUtil;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: ZH
 * @Description:
 * @Date: 19-9-24 下午4:04
 */
public class encryptionTest {

	public static final String text = "zhuhui";
	@Test
	public void test1() throws NoSuchAlgorithmException {
		String str = EncryptionUtil.getBCryptEnCode("123456");
		System.out.println(str);
	}


}
