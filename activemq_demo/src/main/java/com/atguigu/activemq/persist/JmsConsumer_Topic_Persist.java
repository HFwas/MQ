package com.atguigu.activemq.persist;

import java.io.IOException;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsConsumer_Topic_Persist {
	public static final String ACTIVEMQ_URL = "tcp://192.168.136.137:61616";
    public static final String TOPIC_NAME = "topic-atguigu";
 
    public static void main(String[] args) throws JMSException, IOException {
 
        System.out.println("***z4");
        //1.创建连接工场,按照给定的URL地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory =new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("z4"); //谁订阅了主题
 
        //3.创建会话session，
        // 两个参数，第一个参数是否以事物提交，第二个参数是默认的签收方式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);
 
        /*
           1、一定要先运行一次消费者，等于向MQ注册，类似我订阅了这个主题
           2、然后再运行生产者发送消息，此时，
           3、无论消费者是否在线，都会接收到，不在线的话，下次连接的时候，会把没有收过的消息都接收下来
         */
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic,"remark..."); //创建持久化的订阅
 
        connection.start();
 
        Message message = topicSubscriber.receive();
        while (null != message){
            TextMessage textMessage = (TextMessage)message;
            System.out.println("*****收到的持久化topic:"+textMessage.getText());
            message = topicSubscriber.receive();
        }
        session.close();
        connection.close();
 
    }
}
