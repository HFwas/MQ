package com.atguigu.activemq.spring;

import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpringMQ_Produce {
	@Autowired
    private JmsTemplate jmsTemplate;
 
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_Produce produce = (SpringMQ_Produce) ctx.getBean("springMQ_Produce");
        produce.jmsTemplate.send((session)->{
            TextMessage textMessage = session.createTextMessage("*****spring和ActiveMQ的整合case111.....");
            return textMessage;
        });
 
        System.out.println("*********send task over");
 
    }
}	
