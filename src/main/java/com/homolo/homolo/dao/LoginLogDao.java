package com.homolo.homolo.dao;

import com.homolo.homolo.entity.logs.LoginLog;

/**
 * 用户登录日志.
 */
public interface LoginLogDao {
	int generateLog(LoginLog log);
}
