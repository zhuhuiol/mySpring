/**
 * Project:justice-mediation-webapp
 * File:Rc4Util.java
 * Copyright 2004-2018 Homolo Co., Ltd. All rights reserved.
 */
package com.homolo.homolo.utils;

import java.io.UnsupportedEncodingException;

/**
 * rc4.
 */
public final class Rc4Util {
	private Rc4Util() {
	}

	private static final String ENCODING = "UTF-8"; // 编码

	/**
	 * key.
	 */
	public final static String key = "zhspringboot";

	public static byte[] encry_RC4_byte(String data, String key) throws UnsupportedEncodingException {
		if (data == null || key == null) {
			return new byte[0];
		}
		return RC4Base(data.getBytes(ENCODING), key);
	}

	public static byte[] encry_RC4_byte(byte[] data, String key) throws UnsupportedEncodingException {
		if (data == null || key == null) {
			return new byte[0];
		}
		return RC4Base(data, key);
	}

	/**
	 * 功能描述：初始密钥key.
	 *
	 * @param aKey
	 *            密钥串
	 * @return byte[]
	 * @throws UnsupportedEncodingException
	 *             UnsupportedEncodingException
	 */
	private static byte[] initKey(String aKey) throws UnsupportedEncodingException {
		byte[] b_key = aKey.getBytes(ENCODING); // .getBytes();
		byte[] state = new byte[256];

		for (int i = 0; i < 256; i++) {
			state[i] = (byte) i;
		}
		int index1 = 0;
		int index2 = 0;
		if (b_key.length == 0) {
			return new byte[0];
		}
		for (int i = 0; i < 256; i++) {
			index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
			byte tmp = state[i];
			state[i] = state[index2];
			state[index2] = tmp;
			index1 = (index1 + 1) % b_key.length;
		}
		return state;
	}

	/**
	 * 功能描述：RC4加密算法.
	 *
	 * @param input
	 *            明文字节数组
	 * @param mKkey
	 *            密钥
	 * @return byte[] 密文字节数组
	 * @throws UnsupportedEncodingException
	 *             UnsupportedEncodingException
	 */
	private static byte[] RC4Base(byte[] input, String mKkey) throws UnsupportedEncodingException {
		int x = 0;
		int y = 0;
		byte[] key = initKey(mKkey);
		int xorIndex;
		byte[] result = new byte[input.length];
		for (int i = 0; i < input.length; i++) {
			x = (x + 1) & 0xff;
			y = ((key[x] & 0xff) + y) & 0xff;
			byte tmp = key[x];
			key[x] = key[y];
			key[y] = tmp;
			xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
			result[i] = (byte) (input[i] ^ key[xorIndex]);
		}
		return result;
	}

	/**
	 * 加密.
	 *
	 * @param data
	 *            data
	 * @param key
	 *            key
	 * @return String
	 * @throws UnsupportedEncodingException
	 *             UnsupportedEncodingException
	 */
	public static String getEnData(String data, String key) throws UnsupportedEncodingException {
		org.apache.commons.codec.binary.Base64 b64 = new org.apache.commons.codec.binary.Base64();
		byte[] b = RC4Base(data.getBytes(ENCODING), key);
		String result = b64.encodeAsString(b);
		return result;
	}

	/**
	 * 解密.
	 *
	 * @param data
	 *            data
	 * @param key
	 *            key
	 * @return String
	 * @throws UnsupportedEncodingException
	 *             UnsupportedEncodingException
	 */
	public static String getDeData(String data, String key) throws UnsupportedEncodingException {
		org.apache.commons.codec.binary.Base64 b64 = new org.apache.commons.codec.binary.Base64();
		String result = new String(RC4Base(b64.decode(data), key), ENCODING);
		return result;
	}

}
