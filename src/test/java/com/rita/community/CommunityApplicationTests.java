package com.rita.community;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {

    @Test
    public void contextLoads() {
    }
    @Test
    public void arrayy(){
        String message = "123:123";
        String[] array = message.split("\\;");
        System.out.println(array[0]);
        System.out.println("收到消息id:"+array[0]+"pwd:"+array[1]);



    }

}
