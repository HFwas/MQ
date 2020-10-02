package com.atguigu.boot.activemq.topic.config;

import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigBean {
	
	@Value("${mytopic}")
    private String topicName;
 
    @Bean
    public Topic topic(){
        return new ActiveMQTopic(topicName);
    }
}
