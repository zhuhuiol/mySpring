package com.homolo.homolo.service.impl;

import com.homolo.homolo.dao.LoginLogDao;
import com.homolo.homolo.entity.logs.LoginLog;
import com.homolo.homolo.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 日志记录逻辑.
 */
@Component
public class LoginLogServiceImpl implements LoginLogService {

	@Autowired
	private LoginLogDao loginLogDao;

	@Override
	public int generateLog(LoginLog log) {
		return this.loginLogDao.generateLog(log);
	}
}
