package com.hhf.boot.activemq.topic.consumer;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Topic_Consumer {
	@JmsListener(destination = "${mytopic}")
    public void receive(TextMessage text)throws JMSException{
        try {
            System.out.println("消费者收到订阅者的主题："+text.getText());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
