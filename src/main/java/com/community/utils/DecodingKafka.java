package com.community.utils;

import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class DecodingKafka implements Deserializer<Object> {
    @Override
    public void close() {
        // TODO Auto-generated method stub
    }
    @Override
    public void configure(Map<String, ?> arg0, boolean arg1) {
        // TODO Auto-generated method stub
    }
    @Override
    public Object deserialize(String topic, byte[] data) {
        // 只需重写此方法即可
        return BeanUtils.byteToObj(data);
    }

}
