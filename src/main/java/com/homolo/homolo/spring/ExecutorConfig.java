package com.homolo.homolo.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/** .
 * @Author: ZH
 * @Description: 线程配置类. @Async("asyncServiceExecutor")使用
 * @Date: 19-8-26 下午2:06
 */
@Configuration
@EnableAsync
public class ExecutorConfig {
	@Bean
	public Executor asynsServiceExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(300); //核心线程数
		executor.setMaxPoolSize(500); //最大线程数
		executor.setQueueCapacity(9999); //等待执行线程的队列大小
		executor.setThreadNamePrefix("asyns-service-"); //设置线程前缀名称
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //失败处理-重新执行此线程
		executor.initialize();
		return executor;
	}
}
