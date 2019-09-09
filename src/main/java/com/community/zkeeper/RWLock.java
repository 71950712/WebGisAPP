package com.community.zkeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkImpl;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class RWLock {
    @Autowired
    private CuratorFramework client;


    //4 分布式锁
     private InterProcessReadWriteLock lock = new InterProcessReadWriteLock(client, "/readwriter");
     private InterProcessMutex readLock = lock.readLock();
     private InterProcessMutex writeLock = lock.writeLock();
     public void getWriteLock() {
         try {
             writeLock.acquire();
             System.out.println("已拿到写锁");
         }catch (Exception e){
             System.out.println("error in acquiring");
         }
     }
     public void removeWriteLock(){
         try {
             writeLock.release();
             System.out.println("已释放写锁");
         } catch (Exception e) {
             System.out.println("error in release");
         }
     }
}
