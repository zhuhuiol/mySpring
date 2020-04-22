package com.homolo.homolo.service.impl;

import com.homolo.homolo.annotations.SystemLog;
import com.homolo.homolo.dao.UserServiceDao;
import com.homolo.homolo.enums.LogType;
import com.homolo.homolo.enums.OperationLogType;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: ZH
 * @Description:
 * @Date: 19-9-10 下午4:59
 */
@Component("userDetailsManager")
public class UserDateilServiceImpl implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(UserDateilServiceImpl.class);

	@Autowired
	private UserServiceDao userServiceDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.homolo.homolo.entity.User userInfo = this.userServiceDao.loadUserByUsername(username);
		if (userInfo == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		//在此构造方法处设置用户的锁定，禁用等
		User user  = new User(userInfo.getUsername(), userInfo.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
		userInfo.setUser(user);
		return userInfo;
	}

	//默认事务
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@SystemLog(operationType = OperationLogType.I, operationName = "增加测试数据", type = LogType.OPERATION)
	public void testI(int num) {
		StopWatch stopWatch = StopWatch.createStarted();
		if (num < 1) {
			num = 1;
		}
		for (int i = 0; i < num; i++) {
			this.userServiceDao.testI();
			logger.info("执行插入，第" + ++i + "条");
		}
		logger.info("this testI is cost: " + stopWatch.getTime() + "ms");
	}

	public void testBatchInsertProcedure(Integer num) {
		logger.info("批量插入数据" + num + "条");
		StopWatch stopWatch = StopWatch.createStarted();
		this.userServiceDao.testBatchInsertProcedure(num);
		logger.info("this testBatchInsertProcedure is cost: " + stopWatch.getTime() + "ms");
	}

	//创建测试用户
	public String createTestUser(com.homolo.homolo.entity.User user) {
		com.homolo.homolo.entity.User userInfo = this.userServiceDao.loadUserByUsername(user.getUsername());
		if (userInfo == null) {
			this.userServiceDao.createTestUser(user);
			return "用户创建成功";
		} else {
			return "用户已存在";
		}
	}

}
