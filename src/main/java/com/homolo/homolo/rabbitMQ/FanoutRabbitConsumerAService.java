package com.homolo.homolo.rabbitMQ;

import com.homolo.homolo.entity.User;
import com.homolo.homolo.utils.SerializableUtil;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @Author: ZH
 * @Description: 默认.
 * @Date: 20-3-12 下午5:20
 */
//@Component
//@RabbitListener(queues = "fanout.A")
public class FanoutRabbitConsumerAService implements ChannelAwareMessageListener {

	Logger logger = LoggerFactory.getLogger(getClass());

//	@RabbitHandler
//	public void getMessage(List message) {
//		logger.info("opicRabbitMQ-A接收消息:" + message.size());
//	}

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		//手动确认
		logger.info("opicRabbitMQ-A接收消息:" + message.toString());
		long deliveryTag = message.getMessageProperties().getDeliveryTag();
		try {
			List list = SerializableUtil.Deserialize(message.getBody(), List.class);
			logger.info("opicRabbitMQ-A接收消息-size:" + list.size());
			channel.basicAck(deliveryTag, false);
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
