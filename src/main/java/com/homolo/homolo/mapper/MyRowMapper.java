package com.homolo.homolo.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

/**
 * @Author homolo
 * @DESC
 * @Create 2019-08-23  下午2:38
 */
public class MyRowMapper<T> implements RowMapper<T> {
	/**
	 * 添加字段注释.
	 */
	private Class<?> targetClazz;

	/**
	 * 添加字段注释.
	 */
	private HashMap<String, Field> fieldMap;
	/**
	 * 构造函数.
	 *
	 * @param targetClazz
	 *            .
	 */
	public MyRowMapper(Class<?> targetClazz) {
		this.targetClazz = targetClazz;
		fieldMap = new HashMap<>();
		Field[] fields = targetClazz.getDeclaredFields();
		for (Field field : fields) {
			// 同时存入大小写，如果表中列名区分大小写且有列ID和列iD，则会出现异常。
			// 阿里开发公约，建议表名、字段名必须使用小写字母或数字；禁止出现数字开头，禁止两个下划线中间只出现数字。
			fieldMap.put(field.getName(), field);
			// fieldMap.put(getFieldNameUpper(field.getName()), field);
		}
	}
	@Override
	public T mapRow(ResultSet rs, int j) throws SQLException {
		T obj = null;
		try {
			obj = (T) targetClazz.newInstance();

			final ResultSetMetaData metaData = rs.getMetaData();
			int columnLength = metaData.getColumnCount();
			String columnName = null;
			for (int i = 1; i <= columnLength; i++) {
				columnName = metaData.getColumnName(i);
				Class fieldClazz = fieldMap.get(columnName).getType();
				Field field = fieldMap.get(columnName);
				field.setAccessible(true);

				// fieldClazz == Character.class || fieldClazz == char.class
				if (fieldClazz == int.class || fieldClazz == Integer.class) { // int
					field.set(obj, rs.getInt(columnName));
				} else if (fieldClazz == boolean.class || fieldClazz == Boolean.class) { // boolean
					field.set(obj, rs.getBoolean(columnName));
				} else if (fieldClazz == String.class) { // string
					field.set(obj, rs.getString(columnName));
				} else if (fieldClazz == float.class) { // float
					field.set(obj, rs.getFloat(columnName));
				} else if (fieldClazz == double.class || fieldClazz == Double.class) { // double
					field.set(obj, rs.getDouble(columnName));
				} else if (fieldClazz == BigDecimal.class) { // bigdecimal
					field.set(obj, rs.getBigDecimal(columnName));
				} else if (fieldClazz == short.class || fieldClazz == Short.class) { // short
					field.set(obj, rs.getShort(columnName));
				} else if (fieldClazz == Date.class) { // date
					field.set(obj, rs.getDate(columnName));
				} else if (fieldClazz == Timestamp.class) { // timestamp
					field.set(obj, rs.getTimestamp(columnName));
				} else if (fieldClazz == Long.class || fieldClazz == long.class) { // long
					field.set(obj, rs.getLong(columnName));
				}

				field.setAccessible(false);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return obj;
	}
}
