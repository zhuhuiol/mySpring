package com.homolo.homolo.rabbitMQConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ZH
 * @Description: 扇形交换机－生产者.
 * 绑定三个队列，扇形交换机无需配置routingKey,不起作用
 * @Date: 20-3-12 下午5:04
 */
//@Configuration
public class FanoutRabbitConfig {

	@Bean
	public Queue queueA() {
		return new Queue("fanout.A");
	}

	@Bean
	public Queue queueB() {
		return new Queue("fanout.B");
	}

	@Bean
	public Queue queueC() {
		return new Queue("fanout.C");
	}

	@Bean
	FanoutExchange fanoutExchange() {
		return new FanoutExchange("FanoutExchange");
	}

	@Bean
	Binding bindingA() {
		return BindingBuilder.bind(queueA()).to(fanoutExchange());
	}

	@Bean
	Binding bindingB() {
		return BindingBuilder.bind(queueB()).to(fanoutExchange());
	}

	@Bean
	Binding bindingC() {
		return BindingBuilder.bind(queueC()).to(fanoutExchange());
	}

}
