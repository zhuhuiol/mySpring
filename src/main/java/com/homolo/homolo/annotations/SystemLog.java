package com.homolo.homolo.annotations;

import com.homolo.homolo.enums.LogType;
import com.homolo.homolo.enums.OperationLogType;

import java.lang.annotation.*;

/**
 * 操作日志记录.
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
	//操作日志类型(增删改查)
	OperationLogType operationType();
	//操作日志描述
	String operationName();
	//日志类型(操作日志/登录日志)
	LogType type();

}
