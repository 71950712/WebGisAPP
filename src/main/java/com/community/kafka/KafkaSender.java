package com.community.kafka;

import com.community.bean.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 生产者
 */
@Component
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 发送消息到kafka
     * channel=topics
     */
    public void sendChannelMess(String channel, Account message){

        kafkaTemplate.send(channel,message);
        System.out.println("正在发送消息"+message.toString());
    }
}
