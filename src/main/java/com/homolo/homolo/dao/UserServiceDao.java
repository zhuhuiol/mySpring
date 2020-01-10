package com.homolo.homolo.dao;

import com.homolo.homolo.entity.User;
/**
 * @Author: zh
 * @Description: 用户接口.
 * @Date: Created on 下午1:15 19-9-26
 */
public interface UserServiceDao {
	//根据用户名加载用户
	User loadUserByUsername(String username);
	//测试插入数据
	int testI();
	//测试用存储过程批量插入数据
	void testBatchInsertProcedure(Integer num);
}
