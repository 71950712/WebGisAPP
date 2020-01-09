package com.community.utils;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;
/*
    对消息进行序列化，把对象转为字符串信息
*/
public class EncodingKafka implements Serializer<Object> {

    public EncodingKafka() {
        // TODO Auto-generated constructor stub
    }
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // TODO Auto-generated method stub
    }
    @Override
    public byte[] serialize(String topic, Object data) {
        //只需重写这个方法即可
        return BeanUtils.beanToByte(data);
    }
    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

}
