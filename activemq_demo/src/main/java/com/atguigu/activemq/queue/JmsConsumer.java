package com.atguigu.activemq.queue;

import java.io.IOException;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsConsumer {
	//public static final String ACTIVEMQ_URL = "tcp://192.168.43.128:61616";
	public static final String ACTIVEMQ_URL = "nio://192.168.43.128:61608";
	//public static final String QUEUE_NAME = "queue01";
    public static final String QUEUE_NAME = "nioauto";
 
    public static void main(String[] args) throws JMSException, IOException {
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
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
 
     /*
		     第一种访问方式：
		     同步阻塞方式（receive()）
		     订阅者或接收者调用messageConsumer的receive()方法来接收消息，receive方法在能够接收到消息之前(或超时之前)将一直阻塞.
 
     while (true){
            //记住：发送的是什么格式，自然接收的格式也要跟发送的格式一样
           // TextMessage textMessage =(TextMessage) messageConsumer.receive(); //不带时间的receive()方法，说明这方法一直在等待新消息。
            TextMessage textMessage =(TextMessage) messageConsumer.receive(4000L);  //带时间的receive()方法，说明该方法一旦到了时间就立刻结束接收。
            if (null != textMessage){
                System.out.println("****消费者接收到消息:"+textMessage.getText());
            }else {
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();*/
 
 
        /**
         *   第二种访问方式：
         *      能够解决消费者的监听，通过监听的方式消费消息  MessageConsumer messageConsumer = session.createConsumer(queue);
         *      异步阻塞方式（监听器onMessage()）
         *      订阅者或接收者通过messageConsumer的setMessageListener(MessageListener Listener)注册一个消息监听器
         *      当消息到达之后，系统自动调用监听器MessageListener的onMessage(Message message)方法
         */
        messageConsumer.setMessageListener(new MessageListener() {  //设置监听器，消费者监听消息，如果有消息就消费，如果没有消息就不管
            @Override
            public void onMessage(Message message) {  //该方法是盯着消息来监听的方法
                if (null != message && message instanceof  TextMessage){
                        TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("****消费者接收到消息:"+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();  //保证控制台不关
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
