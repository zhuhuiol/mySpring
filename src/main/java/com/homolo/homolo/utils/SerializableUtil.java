package com.homolo.homolo.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @Author: ZH
 * @Description: 序列化工具类.
 * @Date: 20-3-13 上午10:27
 */
public class SerializableUtil {

	/**
	 * 反序列化.
	 * @param bytes
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static <T> T Deserialize(byte [] bytes, Class<T> type) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
		return type.cast(ois.readObject());
	}


}
