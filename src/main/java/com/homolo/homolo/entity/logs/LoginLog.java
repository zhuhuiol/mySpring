package com.homolo.homolo.entity.logs;

import lombok.Data;

import java.util.Date;

/**
 * 系统用户登录日志.
 */
@Data
public class LoginLog {

	private String id;
	//ip
	private String ip;
	//用户名
	private String userName;
	//日期
	private Date loginDate;
	//操作类型/登录登出
	private String loginLogType;
	//结果1成功， 9失败
	private int result;
	//消息描述
	private String message;
}
