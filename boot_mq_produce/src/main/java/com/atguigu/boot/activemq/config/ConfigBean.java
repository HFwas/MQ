package com.atguigu.boot.activemq.config;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

@Component
@EnableJms   //代表开启适配的注解
public class ConfigBean {
	
	@Value("${myqueue}")
    private String myQueue;
 
    @Bean
    public Queue queue(){
        return new ActiveMQQueue(myQueue);
    }

}
