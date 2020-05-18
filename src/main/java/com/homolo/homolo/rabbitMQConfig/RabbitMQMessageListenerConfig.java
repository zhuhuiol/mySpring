package com.homolo.homolo.rabbitMQConfig;

import com.homolo.homolo.rabbitMQ.DirectRabbitConsumerService;
import com.homolo.homolo.rabbitMQ.DirectRabbitConsumerService2;
import com.homolo.homolo.rabbitMQ.FanoutRabbitConsumerAService;
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
//@Configuration
public class RabbitMQMessageListenerConfig {


	@Autowired
	private CachingConnectionFactory cachingConnectionFactory;

	@Autowired
	private DirectRabbitConfig directRabbitConfig;

	@Autowired
	private DirectRabbitConsumerService directRabbitConsumerService;

	@Autowired
	private DirectRabbitConsumerService2 directRabbitConsumerService2;

	@Autowired
	private FanoutRabbitConfig fanoutRabbitConfig;

	@Autowired
	private FanoutRabbitConsumerAService fanoutRabbitConsumerAService;

	@Bean
	public SimpleMessageListenerContainer simpleMessageListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cachingConnectionFactory);
		container.setConcurrentConsumers(1); //设置并发消费者
		container.setMaxConcurrentConsumers(2); //设置最大并发消费者
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //消息改为手动确认，默认为自动确认
		//配置队列以及监听器，　否则收不到消息
		container.setQueues(this.directRabbitConfig.directQueue());
		container.setMessageListener(this.directRabbitConsumerService);
		container.setMessageListener(this.directRabbitConsumerService2);

		container.setQueues(this.fanoutRabbitConfig.queueA());
		container.setMessageListener(this.fanoutRabbitConsumerAService);

		return container;
	}
}
