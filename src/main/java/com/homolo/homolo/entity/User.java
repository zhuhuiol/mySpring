package com.homolo.homolo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author homolo--
 * @DESC
 * @Create 2019-08-23  下午12:18
 */
@Data
public class User {

	private String userid;

	private String username;

	private String password;

	private String usernick;

	private Date birthday;

	private int age;

	private int sex;

	private String email;

	private int disabled;

	private String mobile;

	private String description;

	private String avatar;

	private String idnunber;

	private String address;

	private Date create_time;

	private Date update_time;

}
