package com.community.service.impl;

import com.community.bean.Account;
import com.community.mapper.AccountMapper;
import com.community.service.IAccountService;
import com.community.zkeeper.RWLock;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    private CuratorFramework client;
    @Autowired
    public RWLock lock;

    public void insertOne(Account account){
       lock.getWriteLock();
         accountMapper.insertOne(account.getId(),account.getPassword());
         lock.removeWriteLock();
    }


    public List<Account> getAccountById(int id) {
        return accountMapper.selectById(id);

    }

    public Account getPwdById(int id) {
        InterProcessReadWriteLock lock = new InterProcessReadWriteLock(client, "/readwriter");
        InterProcessMutex readLock = lock.readLock();
        InterProcessMutex writeLock = lock.writeLock();
        try {
            writeLock.acquire();
            System.out.println("已拿到写锁");

            return accountMapper.selectPwdById(id);

        } catch (Exception e) {
            System.out.println("error in acquiring");
            return null;
        } finally {

            try {
                writeLock.release();
                System.out.println("已释放写锁");
            } catch (Exception e) {
                System.out.println("error in release");
            }

        }


    }
}
