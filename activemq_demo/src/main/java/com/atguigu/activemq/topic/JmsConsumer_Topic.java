package com.atguigu.activemq.topic;

import java.io.IOException;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsConsumer_Topic {
	public static final String ACTIVEMQ_URL = "tcp://192.168.136.137:61616";
    public static final String TOPIC_NAME = "topic-atguigu";
 
    public static void main(String[] args) throws JMSException, IOException {
 
        System.out.println("***我是二号消费者");
        //1.创建连接工场,按照给定的URL地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory =new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start(); //连上地址
 
        //3.创建会话session，
        // 两个参数，第一个参数是否以事物提交，第二个参数是默认的签收方式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        //Destination destination = session.createQueue(QUEUE_NAME); 可以赋给目的地，而不直接赋给具体的队列还是主题，但是一般不建议这么做
        Topic topic = session.createTopic(TOPIC_NAME);
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);
 
        //通过监听的方式来消费消息
        messageConsumer.setMessageListener((message) ->{
            if (null != message && message instanceof  TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("****消费者接收到Topic消息:" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();  //保证控制台不关
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
