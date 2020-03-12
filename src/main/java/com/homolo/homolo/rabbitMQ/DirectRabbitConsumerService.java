package com.homolo.homolo.rabbitMQ;

import com.homolo.homolo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: ZH
 * @Description: 默认.
 * @Date: 20-3-12 下午3:46
 */
@Component
@RabbitListener(queues = "DirectQueue")
public class DirectRabbitConsumerService {
	Logger logger = LoggerFactory.getLogger(getClass());

	@RabbitHandler
	public void process(User user) {
		logger.info("DirectRabbitMQ接收消息：" + user.getAddress());
	}

}
