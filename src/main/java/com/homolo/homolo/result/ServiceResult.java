package com.homolo.homolo.result;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: ZH
 * @Description: 统一返回格式.
 * @Date: 20-4-8 上午11:25
 */
public class ServiceResult {

	private int code; // 返回代码

	private String description; // 描述

	private Object result; // 结果

	public ServiceResult() {
	}

	public ServiceResult(int code) {
		this(code, null);
	}
	public ServiceResult(int code, String description) {
		this(code, description, null);
	}

	public ServiceResult(int code, String description, Object result) {
		this(code, description, result, true);
	}

	public ServiceResult(int code, Object result) {
		this(code, null, result, true);
	}

	public ServiceResult(int code, String description, Object result, boolean structSupport) {
		this.code = code;
		this.description = description;
		this.result = cloneResultObject(result, structSupport);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object cloneResultObject(Object result, boolean structSupport) {
		if (result instanceof Throwable) {
			Throwable t = (Throwable) result;
			return t.getClass().getName() + ":" + t.getMessage();
		} else {
			if (result instanceof Set) {
				Set originalSet = (Set) result;
				Set clonedSet = new HashSet();
				for (Object object : originalSet) {
					clonedSet.add(cloneResultObject(object, structSupport));
				}
				return clonedSet;
			} else if (result instanceof List) {
				List list0 = (List) result;
				List list1 = new ArrayList();
				for (int i = 0; i < list0.size(); i++) {
					list1.add(cloneResultObject(list0.get(i), structSupport));
				}
				return list1;
			} else if (result.getClass().isArray()) {
				List list = new ArrayList();
				for (int i = 0, l = Array.getLength(result); i < l; i++) {
					list.add(cloneResultObject(Array.get(result, i), structSupport));
				}
				return list;
			} else if (result instanceof Map) {
				Map map0 = (Map) result;
				Map map1 = new HashMap();
				Iterator iterator = map0.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry) iterator.next();
					map1.put(entry.getKey(), cloneResultObject(entry.getValue(), structSupport));
				}
				return map1;
			} else if (result instanceof Enum || result instanceof ResultSet) {
				return result;
			} else if (result instanceof JSONObject || result instanceof JSONArray) {
				return result;
			} else {
				return "都传的啥啊";
			}
		}
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
	}

}
