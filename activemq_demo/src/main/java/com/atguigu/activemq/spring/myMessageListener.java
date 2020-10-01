package com.atguigu.activemq.spring;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

@Component
public class myMessageListener implements javax.jms.MessageListener {

	@Override
	public void onMessage(Message message) {
		if (null != message && message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage)message;
			try {
				System.out.println("接受信息為："+textMessage.getText());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
