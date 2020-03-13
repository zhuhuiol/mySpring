package com.homolo.homolo.spring;

import com.homolo.homolo.rabbitMQ.DirectRabbitConsumerService;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ZH
 * @Description: mq消息监听配置.
 * @Date: 20-3-13 上午9:42
 */
@Configuration
public class RabbitMQMessageListenerConfig {


	@Autowired
	private CachingConnectionFactory cachingConnectionFactory;

	@Autowired
	private DirectRabbitConfig directRabbitConfig;

	@Autowired
	private DirectRabbitConsumerService directRabbitConsumerService;

	@Bean
	public SimpleMessageListenerContainer simpleMessageListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cachingConnectionFactory);
		container.setConcurrentConsumers(1); //设置并发消费者
		container.setMaxConcurrentConsumers(2); //设置最大并发消费者
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //消息改为手动确认，默认为自动确认
		container.setQueues(this.directRabbitConfig.directQueue());
		container.setMessageListener(this.directRabbitConsumerService);

		return container;
	}
}
