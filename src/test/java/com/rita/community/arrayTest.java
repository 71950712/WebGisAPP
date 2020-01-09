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
        quickSort quickSort1 = new quickSort();
        int[] a = {15,985,5,7,24,1,23,58,4,94};
        int tag = 0;
        int low =0;
        int high = a.length - 1;
        System.out.println(a.length);
        System.out.println(high);
        quickSort1.quickSortImpl(a,low,high);


    }



}
