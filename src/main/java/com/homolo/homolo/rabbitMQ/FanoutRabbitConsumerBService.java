package com.homolo.homolo.rabbitMQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: ZH
 * @Description: 默认.
 * @Date: 20-3-12 下午5:20
 */
@Component
@RabbitListener(queues = "fanout.B")
public class FanoutRabbitConsumerBService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@RabbitHandler
	public void getMessage(List message) {
		logger.info("opicRabbitMQ-B接收消息 "+ message.get(0));
	}

}
