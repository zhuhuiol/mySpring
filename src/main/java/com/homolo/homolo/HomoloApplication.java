package com.homolo.homolo;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan({"com.homolo.homolo.dao"})
public class HomoloApplication {

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		//前置路径
		return new ServletRegistrationBean(new CXFServlet(), "/ws/service/*");
	}
	public static void main(String[] args) {
		SpringApplication.run(HomoloApplication.class, args);
	}

}
