package com.homolo.homolo.spring;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ZH
 * @Description: 直连型交换机-生产者.
 * @Date: 20-3-12 下午2:37
 */
@Configuration
public class DirectRabbitConfig {

	/**
	 * 队列　　起名:testDirectQueue.
	 * @return queue
	 */
	@Bean
	public Queue directQueue() {
		return new Queue("DirectQueue"); //true 是否持久
	}
	@Bean
	public Queue directQueue2() {
		return new Queue("DirectQueue2"); //true 是否持久
	}

	/**
	 * 交换机：　起名：DirectExchange.
	 * @return DirectExchange
	 */
	@Bean
	DirectExchange directExchange() {
		return new DirectExchange("DirectExchange"); //默认持久化，不自动删除
	}


	/**
	 * 为队列绑定交换机,设置匹配键:DirectRouting.
	 * @return Binding
	 */
	@Bean
	Binding directBinding() {
		return BindingBuilder.bind(directQueue()).to(directExchange()).with("DirectRouting");
	}
	@Bean
	Binding directBinding2() {
		return BindingBuilder.bind(directQueue2()).to(directExchange()).with("DirectRouting2");
	}


}
