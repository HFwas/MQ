package com.atguigu.activemq.async;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsConsumer {
	// public static final String ACTIVEMQ_URL = "tcp://192.168.43.128:61616";
	public static final String ACTIVEMQ_URL = "tcp://192.168.43.128:61608";
	// public static final String QUEUE_NAME = "queue01";
	public static final String QUEUE_NAME = "queue_async";

	public static void main(String[] args) throws JMSException, IOException {
		// 1.创建连接工场,按照给定的URL地址，采用默认用户名和密码
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		// 2.通过连接工场，获得连接connection并启动访问
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start(); // 连上地址

		// 3.创建会话session，
		// 两个参数，第一个参数是否以事物提交，第二个参数是默认的签收方式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 4.创建目的地（具体是队列还是主题topic）
		// Destination destination = session.createQueue(QUEUE_NAME);
		// 可以赋给目的地，而不直接赋给具体的队列还是主题，但是一般不建议这么做
		Queue queue = session.createQueue(QUEUE_NAME);
		// 5.创建消费者
		MessageConsumer messageConsumer = session.createConsumer(queue);

		messageConsumer.setMessageListener(new MessageListener() { // 设置监听器，消费者监听消息，如果有消息就消费，如果没有消息就不管
			@Override
			public void onMessage(Message message) { // 该方法是盯着消息来监听的方法
				if (null != message && message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						System.out.println("****消费者接收到消息:" + textMessage.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		System.in.read(); // 保证控制台不关
		messageConsumer.close();
		session.close();
		connection.close();
	}
}
