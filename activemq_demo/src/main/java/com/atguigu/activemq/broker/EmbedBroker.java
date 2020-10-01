package com.atguigu.activemq.broker;

import org.apache.activemq.broker.BrokerService;

public class EmbedBroker {
	public static void main(String[] args) throws Exception {
		//Broker其实就是实现了用代码的形式启动ActiveMQ将MQ嵌入到Java代码中，以便随时用随时启动，
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}	
