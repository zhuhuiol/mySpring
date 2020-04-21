package com.homolo.homolo.entity.logs;

import lombok.Data;

import java.util.Date;

/**
 * 系统操作日志.
 */
@Data
public class OperationLog {

	//数据id
	private String id;
	//描述
	private String description;
	//方法
	private String method;
	//请求ip
	private String requestIp;
	//创建时间
	private Date createDate;
	//操作用户
	private String userName;
	//操作日志类型
	private String operationType;
}
