package com.homolo.homolo.rabbitMQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: ZH
 * @Description: 默认.
 * @Date: 20-3-12 下午4:44
 */
//@Component
//@RabbitListener(queues = "topic.hui")
public class TopicRabbitConsumerHuiService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@RabbitHandler
	public void getMessage(Map message) {
		logger.info("TopicRabbitMQ-hui接收消息" + message.toString());
	}
}
