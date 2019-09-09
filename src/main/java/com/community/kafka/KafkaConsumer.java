package com.community.kafka;

import com.community.bean.Account;
import com.community.service.IAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 消费者 spring-kafka 2.0 + 依赖JDK8
 * @author 科帮网 By https://blog.52itstyle.com
 */
@Component
public class KafkaConsumer {
	@Autowired
	private IAccountService accountService;


    /**
     * 监听register主题,有消息就读取
     * @param message
     */

    @KafkaListener(topics = {"register"})
    public void receiveMessage(Account message){
    	//收到通道的消息之后执行秒杀操作
    	//String[] array = message.split("\\:");
        System.out.println("收到消息id:"+message.getId()+"pwd:"+message.getPassword());
        accountService.insertOne(message);
    }


}