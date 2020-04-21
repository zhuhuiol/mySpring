package com.homolo.homolo.utils;

import java.util.UUID;

public class UUIDUtil {

	public static String generateUUID() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
	public static String generateUUID(type type) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		uuid = type.name() + uuid.substring(3, uuid.length());
		return uuid;
	}



	/**
	 * uuid产生类型.
	 */
	public static enum type {
		LOG

	}

}
