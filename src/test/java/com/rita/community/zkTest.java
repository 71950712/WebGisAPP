package com.rita.community;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class zkTest {


    public static void main(String[] args) {
         String address = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

        //1、重试策略：初试时间为1s 重试3次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //2、通过工厂创建连接
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, retryPolicy);
        //3、开启连接
        client.start();
        InterProcessReadWriteLock lock = new InterProcessReadWriteLock(client, "/readwriter");
        InterProcessMutex readLock = lock.readLock();
        InterProcessMutex writeLock = lock.writeLock();

        try {
            writeLock.acquire();

            System.out.println("已拿到写锁");
            Thread t = new Thread(new Runnable() {
                public void run() {
                    System.out.println("Mythread 线程");
                }
            });
            t.start();
            t.interrupt();
   /*         try {
                System.out.println("sleeping");
                t.sleep(120000);
            }catch (Exception e){}
            */

        } catch (Exception e) {
            System.out.println("error in acquiring");
        }finally {
            try {
                writeLock.release();
                System.out.println("已释放写锁");
            } catch (Exception e) {
                System.out.println("error in release");
            }
        }

    }



}
