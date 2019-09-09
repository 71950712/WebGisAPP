package com.community.controller;

import com.community.bean.Account;
import com.community.kafka.KafkaSender;
import com.community.service.IAccountService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AccountController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private KafkaSender kafkaSender;


    @RequestMapping(path="/s")
    public List<Account> getAccountById(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        return  accountService.getAccountById(id);
    }
    @RequestMapping(path="/")
    public String tologin(){
       Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.isRemembered());
        System.out.println(subject.isAuthenticated());
        if(subject.isRemembered()||subject.isAuthenticated()){
            return "account/ts";
        }

        return "Login";
    }
    @RequestMapping(path="/login")
    @ResponseBody
    public boolean login(String id,String pwd){
        System.out.println(id+pwd);
       Subject subject = SecurityUtils.getSubject();
       System.out.println(subject.isRemembered());
           UsernamePasswordToken token = new UsernamePasswordToken(id, pwd);
           token.setRememberMe(false);
        System.out.println(token.isRememberMe());
           //3、执行登录方法
           try {
               //交给Realm处理--->执行它的认证方法
               subject.login(token);
               System.out.println(id+pwd);
               //登录成功
               return true;
           } catch (UnknownAccountException e) {
               //登录失败:用户名不存在
               return false;
           } catch (IncorrectCredentialsException e) {
               //登录失败：密码错误
               return false;
           } catch (NullPointerException e) {
               return false;
           }


    }
    @RequestMapping(path="/register")
    @ResponseBody
    public boolean addAccount(String id,String pwd){
        Account account = new Account();
        System.out.println(id+pwd);
        account.setId(Integer.parseInt(id));
        account.setPassword(pwd);

            kafkaSender.sendChannelMess("register", account);
            return true;

    }

    @RequestMapping(path="/account/add")
    public String add(){
        return "account/add";
    }
    @RequestMapping(path="/account/ts")
    public String ts(){
        return "account/ts";
    }





}
