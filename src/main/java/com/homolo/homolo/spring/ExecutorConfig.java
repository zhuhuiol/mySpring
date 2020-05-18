package com.homolo.homolo.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/** .
 * @Author: ZH
 * @Description: 线程配置类. @Async("name")使用
 * @Date: 19-8-26 下午2:06
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

	private static ThreadPoolTaskExecutor executor;

	private static ExecutorService executorService;

	@Bean(name = "executor")
	public Executor asynsServiceExecutor() {
		if (executor == null) {
			executor = new ThreadPoolTaskExecutor();
		}
		executor.setCorePoolSize(300); //核心线程数
		executor.setMaxPoolSize(500); //最大线程数
		executor.setQueueCapacity(9999); //等待执行线程的队列大小
		executor.setThreadNamePrefix("asyns-service-zh-"); //设置线程前缀名称
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //失败处理-重新执行此线程
		executor.initialize();
		return executor;
	}

	@Bean(name = "executorService")
	public Executor executorService() {
		if (executorService == null) {
			executorService = Executors.newCachedThreadPool();
		}
		return executorService;
	}
	public static ThreadPoolTaskExecutor getExecutor() {
		return executor;
	}

	public static ExecutorService getExecutorService() {
		return executorService;
	}
}
