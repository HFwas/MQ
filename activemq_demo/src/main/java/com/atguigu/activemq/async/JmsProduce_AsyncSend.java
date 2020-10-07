package com.atguigu.activemq.async;

import java.util.UUID;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

public class JmsProduce_AsyncSend {
	public static final String ACTIVEMQ_URL = "tcp://192.168.43.128:61608";
	// public static final String QUEUE_NAME = "queue01";
	public static final String QUEUE_NAME = "queue_async";

	public static void main(String[] args) throws JMSException {
		// 1.创建连接工场,按照给定的URL地址，采用默认用户名和密码
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		activeMQConnectionFactory.setUseAsyncSend(true);
		// 2.通过连接工场，获得连接connection并启动访问
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start(); // 连上地址

		// 3.创建会话session，
		// 两个参数，第一个参数叫：事物，第二个参数叫：签收
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 4.创建目的地（具体是队列还是主题topic）
		// Destination destination = session.createQueue(QUEUE_NAME);
		// 可以赋给目的地，而不直接赋给具体的队列还是主题，但是一般不建议这么做
		Queue queue = session.createQueue(QUEUE_NAME);
		// 5.创建消息的生产者
		ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer) session.createProducer(queue);
		TextMessage message = null;
		// 6.通过使用消息生产者messageProducer生产3条消息发送到MQ的队列里面
		for (int i = 1; i <= 3; i++) {
			// 7.创建消息,好比学生按照老师的要求写好的面试题消息
			message = session.createTextMessage("msg---" + i);// 理解为一个字符串
			message.setJMSMessageID(UUID.randomUUID().toString() + "orderAtguigu");
			String jmsMessageID = message.getJMSMessageID();
			activeMQMessageProducer.send(message, new AsyncCallback() {
				
				@Override
				public void onException(JMSException arg0) {
					System.out.println(jmsMessageID + "ok send");
				}
				
				@Override
				public void onSuccess() {
					System.out.println(jmsMessageID + "fail send");
				}
			});
		}
		// 9.关闭资源
		activeMQMessageProducer.close();
		session.close();
		connection.close();

		System.out.println("*****消息发布到MQ完成");

	}
}
