package com.community.controller;

import com.community.service.IAccountService;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class hellowController {
    @Autowired
    private IAccountService iAccountService;

    @GetMapping(path="/pages")
    public String index(){

        return "index";
    }
    @RequestMapping(path="/pages")
    public String map(){

        return "gisAPI";
    }

    public String login(){
  //         model.addAttribute("name","nmsl");
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

        try{
            t.sleep(120000);
        }catch (Exception e){}
        }catch (Exception e){}
        return "hello world";
    }
}
