package com.tox.utils;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
@PropertySource("classpath:config.properties")
@Component
public class RabbitMQ {
	
	private String exchangeName = "ElecChargeExchange";
    
    private String queueName = "ElecChargeQueue";
    
    private String[] type = {"fanout","direct","topic"};
    
    private String routKey = "profit";
    
    private Channel channel;
    
	/**
     * 构造方法 ，把channel赋值
     */
    public RabbitMQ(){
    	//成员变量赋值
    	this.channel = initChannel();
    	//绑定队列
    	bind(this.channel);
    }
    
    public Channel getChannel(){
    	return channel;
    }
    public String getExchangeName() {
    	return exchangeName;
    }
    
    public String getQueueName() {
    	return queueName;
    }
    
    public String getRoutKey() {
    	return routKey;
    }
	
    /**
     * 初始化通道 
     * @return
     */
    public Channel initChannel() {
    	ConnectionFactory factory = new ConnectionFactory();
//		设置RabbitMQ地址
		factory.setHost("140.143.237.20");
		factory.setUsername("root");
		factory.setPassword("111111");
//		创建一个新的连接
		Connection connection;
		try {
			connection = factory.newConnection();
			//创建一个频道
			Channel channel = connection.createChannel();
			return channel;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    /**
     * @param channel
     * 声明一个队列和交换机，并用routKey绑定
     */
    public void bind(Channel channel){
    	try {
    		/**
        	 * 声明交换机
        	 * 参数：交换机名称，交换机类型(常用类型fanout分发，direct直连，topic模糊匹配)
        	 */
        	channel.exchangeDeclare(exchangeName, type[1],true);
        	/**
        	 * 声明队列
        	 * 参数：队列名称，是否持久化，是否自己的频道可见，是否不用时删除，其它参数
        	 */
        	channel.queueDeclare(queueName, true, false, false, null);
        	/**
        	 * 通过routKey绑定交换机和队列
        	 * 参数：队列名称，交换机名称，routKey(profit利润、分润)
        	 */
        	channel.queueBind(queueName, exchangeName, "profit");
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
