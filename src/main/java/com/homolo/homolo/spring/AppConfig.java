package com.homolo.homolo.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author: ZH
 * @Description:
 * @Date: 19-9-9 下午4:27
 */
@Configuration
@ComponentScan({"com.homolo.homolo.spring"})
@Import({SecurityConfig.class})
public class AppConfig {
	@Override
	public String toString() {
		return super.toString();
	}
}
