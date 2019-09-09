package com.community.service;

import com.community.bean.Account;


import java.util.List;

public interface IAccountService {
   List<Account> getAccountById(int id);
   Account getPwdById(int id);
   void insertOne(Account account);
}
