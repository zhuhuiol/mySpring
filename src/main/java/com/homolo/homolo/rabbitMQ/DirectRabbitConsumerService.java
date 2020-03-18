package com.homolo.homolo.rabbitMQ;

import com.homolo.homolo.entity.User;
import com.homolo.homolo.utils.SerializableUtil;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: ZH
 * @Description: 默认.
 * @Date: 20-3-12 下午3:46
 */
@Component
@RabbitListener(queues = "DirectQueue")
public class DirectRabbitConsumerService implements ChannelAwareMessageListener {
	Logger logger = LoggerFactory.getLogger(getClass());

//	@RabbitHandler
//	public void process(User user) {
//		logger.info("DirectRabbitMQ接收消息：" + user.getAddress());
//	}

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		logger.info("DirectRabbitMQ接收消息：" + message.toString());
		long deliveryTag = message.getMessageProperties().getDeliveryTag();
		try {
			User user = SerializableUtil.Deserialize(message.getBody(), User.class);
			logger.info("DirectRabbitMQ接收消息-address:" + user.getAddress());
			channel.basicAck(deliveryTag, true);
		} catch (IOException e) {
			//重新放回队列
			channel.basicReject(deliveryTag, false);//为true 重新放入队列，否则丢弃或者进入死信队列
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			channel.basicReject(deliveryTag, false);
			e.printStackTrace();
		}

	}
}
