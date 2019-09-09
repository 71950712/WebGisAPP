package com.rita.community;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


public class arrayTest {


    public static void main(String[] args) {
        String message = "123:123";
        String[] array = message.split("\\:");
        System.out.println(array[0]);
        System.out.println("收到消息id:"+array[0]+"pwd:"+array[1]);

    }



}
