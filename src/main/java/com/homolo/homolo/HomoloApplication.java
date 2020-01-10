package com.homolo.homolo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan({"com.homolo.homolo.dao"})
public class HomoloApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomoloApplication.class, args);
	}

}
