package com.atguigu.activemq.async;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

public class JmsProduce_DelayAndSchedule {
	//public static final String ACTIVEMQ_URL = "tcp://192.168.43.128:61616";
		public static final String ACTIVEMQ_URL = "tcp://192.168.43.128:61608";
		//public static final String QUEUE_NAME = "queue01";
		public static final String QUEUE_NAME = "nioauto";
		
		public static void main(String[] args) throws JMSException {
			// 1.创建连接工场,按照给定的URL地址，采用默认用户名和密码
			ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
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
			MessageProducer messageProducer = session.createProducer(queue);
			
			long delay = 3 * 1000;
			long period = 4 * 1000;
			int repeat = 5;
			
			// 6.通过使用消息生产者messageProducer生产3条消息发送到MQ的队列里面
			for (int i = 1; i <= 3; i++) {
				// 7.创建消息,好比学生按照老师的要求写好的面试题消息
				TextMessage message = session.createTextMessage("msg---" + i);// 理解为一个字符串
				
				message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
				message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
				message.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);
				
				messageProducer.send(message);
			}
			// 9.关闭资源
			messageProducer.close();
			session.close();
			connection.close();

			System.out.println("*****消息发布到MQ完成");

		}
}
