package com.homolo.homolo.service.impl;

import com.homolo.homolo.dao.OperationLogDao;
import com.homolo.homolo.entity.logs.OperationLog;
import com.homolo.homolo.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 日志记录逻辑.
 */
@Component
public class OperationLogServiceImpl implements OperationLogService {

	@Autowired
	private OperationLogDao operationLogDao;

	@Override
	public int generateLog(OperationLog log) {
		return this.operationLogDao.generateLog(log);
	}
}
