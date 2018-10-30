package com.tox.compant;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.tox.controller.ElecOrderController;
import com.tox.utils.RabbitMQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

//@Component
public class RabbitListener implements CommandLineRunner{
	private static final Logger logger = LoggerFactory.getLogger(ElecOrderController.class);
	@Override
    public void run(String... strings) throws Exception {
		RabbitMQ rabbitMq = new RabbitMQ();
		Channel channel = rabbitMq.getChannel();
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//声明队列的消费者是carportQueue
		channel.basicConsume("carportQueue", false, consumer);
		while (true) {
			String response = "";
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			response = new String(delivery.getBody(), "UTF-8");
			System.out.println(response);
			logger.info(response);
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
    }
}