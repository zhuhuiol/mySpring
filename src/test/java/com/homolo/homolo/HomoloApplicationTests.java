package com.homolo.homolo;

import com.homolo.homolo.entity.User;
import com.homolo.homolo.mapper.MyRowMapper;
import com.homolo.homolo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomoloApplicationTests {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testCreate() throws Exception {
		System.out.println("插入5个用户");
		this.userService.create("rose", 12);
		Integer allUsers = this.userService.getAllUsers();
		System.out.println(allUsers);
	}

	@Test
	public void testQuery() {
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		User user1 = jdbcTemplate.queryForObject("select * from t_user where id = ?", rowMapper, 1);
		logger.info("单个数据:{}", user1.toString());
		List<User> users = this.userService.queryAllUsers();
		users.forEach(user -> {
			logger.info("姓名为:{}-----id为:{}", user.getUsername(), user.getUserid());
		});
	}

	@Test
	public void testDelete() {
		this.userService.deleteAllUsers(7);
	}
}
