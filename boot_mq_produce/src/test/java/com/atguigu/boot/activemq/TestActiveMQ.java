package com.atguigu.boot.activemq;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.atguigu.boot.activemq.produce.Queue_Produce;

@SpringBootTest(classes = TestActiveMQ.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestActiveMQ {
	
	@Resource
	private Queue_Produce queue_Produce;
	
	@Test
	public void testSend() throws Exception{
		queue_Produce.produceMsg();
	}

}
