package com.homolo.homolo.service;

import com.homolo.homolo.entity.User;

import java.util.List;

/**
 * @Author homolo
 * @DESC
 * @Create 2019-08-23  下午12:19
 */
public interface UserService {
	void create(String name, Integer age);
	void deleteByName(String name);
	Integer getAllUsers();
	List<User> queryAllUsers();
	void deleteAllUsers(Integer id);
}
