package com.homolo.homolo.dao;

import com.homolo.homolo.entity.logs.OperationLog;

/**
 * 用户日志.
 */
public interface OperationLogDao {
	int generateLog(OperationLog log);
}
