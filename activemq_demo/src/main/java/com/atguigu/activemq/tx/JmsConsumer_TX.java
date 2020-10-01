package com.atguigu.activemq.tx;

import java.io.IOException;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsConsumer_TX {
	public static final String ACTIVEMQ_URL = "tcp://192.168.43.128:61616";
    public static final String QUEUE_NAME = "topic-atguigu";
 
    public static void main(String[] args) throws JMSException, IOException {
    	 
        //1.创建连接工场,按照给定的URL地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建会话session，
        // 两个参数，第一个参数是否以事物提交，第二个参数是默认的签收方式
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(queue);
 
        while (true) {
            TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L);
            if (null != textMessage) {
                System.out.println("*****消费者收到的消费：" + textMessage.getText());
               textMessage.acknowledge();
            } else {
                break;
            }
        }
           messageConsumer.close();
           // session.commit();
            session.close();
            connection.close();
        }
}
