package com.homolo.homolo.service;

import com.homolo.homolo.entity.User;
import com.homolo.homolo.mapper.MyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author homolo
 * @DESC
 * @Create 2019-08-23  下午12:20
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void create(String name, Integer age) {
		jdbcTemplate.update("insert into t_user(name, age) values (?,?)", new Object[] {name, age});
	}

	@Override
	public void deleteByName(String name) {

	}

	@Override
	public List<User> queryAllUsers() {
		return jdbcTemplate.query("select * from t_user", new MyRowMapper<>(User.class));
//		return jdbcTemplate.query("select * from t_user", new BeanPropertyRowMapper<>(User.class));
	}

	@Override
	public Integer getAllUsers() {
		return jdbcTemplate.queryForObject("select count(1) from t_user", Integer.class);
//		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
	}

	@Override
	public void deleteAllUsers(Integer id) {
		jdbcTemplate.update("delete from t_user where id=?", id);
	}
}
