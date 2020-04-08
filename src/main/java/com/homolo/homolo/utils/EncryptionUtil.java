package com.homolo.homolo.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static sun.security.x509.CertificateAlgorithmId.ALGORITHM;

/**
 * @Author: ZH
 * @Description: 加密方法
 * @Date: 19-9-24 下午3:46
 */
public class EncryptionUtil {

	// 加密方式长度
	private static final String SHA = "SHA";
	private static final String SHA224 = "SHA-224";
	private static final String SHA256 = "SHA-256";
	private static final String SHA384 = "SHA-384";
	private static final String SHA512 = "SHA-512";
	private static final String MD5 = "MD5";
	private static final String MD2 = "MD2";

	private static final String CIPHER_ALGORITHM = "AES"; // 默认的加密算法，CBC模式 /CBC/PKCS5Padding

	private static final String AES_KEY = "zhspringbootyyyy"; // 密匙，必须16位

	private static final String AES_OFFSET = "5e8y6w45ju8w9jq8"; // 偏移量

	private static final String ENCODING = "UTF-8"; // 编码

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	/**
	 * 加密.
	 * @param obj 需要加密的字符串.
	 * @return 加密后的字符串.
	 */
	public static String encode(String obj) {
		return getEncode(obj, SHA);
	}

	/**
	 * 加密.
	 * @param obj 需要加密的字符串.
	 * @param kdy 加密方式.
	 * @return 加密后的字符串.
	 */
	public static String encode(String obj, String kdy) {
		return getEncode(obj, kdy);
	}

	//加密方法
	private static String getEncode(String obj, String key) {
		if (StringUtils.isEmpty(obj)) {
			return null;
		}

		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(key);
			messageDigest.update(obj.getBytes());
			return getText(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
//			右移＝除以2的4次方，数乘以进制的下标次方
//			二进制以0b开头
//			八进制以0开头
//			十六进制以0x开头
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	/**
	 * Brypt加密方式.
	 * @param text 加密文本
	 * @return 加密后字符串
	 */
	public static String getBCryptEnCode(String text) {
		return new BCryptPasswordEncoder().encode(text);
	}
	/**
	 * Srypt加密方式.
	 * @param text 加密文本
	 * @return 加密后字符串
	 */
	public static String getSCryptEncode(String text) {
		return new SCryptPasswordEncoder().encode(text);
	}

	/**
	 * AES加密.
	 * @param data c
	 * @return c
	 * @throws Exception c
	 */
	public static String aesEncrypt(String data) {
		byte[] encrypted = new byte[0];
		String val = "";
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			SecretKeySpec skeySpec = new SecretKeySpec(AES_KEY.getBytes("ASCII"), ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(AES_OFFSET.getBytes()); //CBC模式偏移量IV
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			encrypted = cipher.doFinal(data.getBytes(ENCODING));
			val = new Base64().encodeToString(encrypted); //加密后再使用BASE64做转码
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return val;
	}

	/**
	 * AES解密.
	 * @param data c
	 * @return c
	 * @throws Exception c
	 */
	public static String aesDecrypt(String data) {
		byte[] encrypted = new byte[0];
		String val = "";
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			SecretKeySpec skeySpec = new SecretKeySpec(AES_KEY.getBytes("ASCII"), ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(AES_OFFSET.getBytes()); //CBC模式偏移量IV
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] buffer = new Base64().decode(data); //先用base64解码
			encrypted = cipher.doFinal(buffer);
			val = new String(encrypted, ENCODING);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return val;
	}

}
