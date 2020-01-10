package com.homolo.homolo.service.impl;

import com.homolo.homolo.dao.UserServiceDao;
import com.homolo.homolo.security.UserDetailInfo;
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
		User user  = new User(userInfo.getUsername(), userInfo.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
//		return new UserDetailInfo(userInfo);
		return user;
	}


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

}
