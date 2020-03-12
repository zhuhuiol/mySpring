package com.homolo.homolo.spring;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ZH
 * @Description: 主题交换机-生产者.
 * @Date: 20-3-12 下午4:04
 */
@Configuration
public class TopicRabbitConfig {

	public static final String key1 = "topic.zhu";
	public static final String key2 = "topic.hui";

	@Bean
	public Queue firstQueue() {
		return new Queue(key1);
	}
	@Bean
	public Queue secondQueue() {
		return new Queue(key2);
	}

	@Bean
	TopicExchange topicExchange() {
		return new TopicExchange("TopicExchange");
	}

	@Bean
	Binding bindingZhu() {
		return BindingBuilder.bind(firstQueue()).to(topicExchange()).with(key1);
	}

	@Bean
	Binding bindingHui() {
		return BindingBuilder.bind(secondQueue()).to(topicExchange()).with("topic.#");
	}

}
