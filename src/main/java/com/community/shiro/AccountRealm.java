package com.community.shiro;

import com.community.bean.Account;
import com.community.service.IAccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountRealm extends AuthorizingRealm {
    @Autowired
    private IAccountService accountService;
    //执行授权逻辑
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        Account curAccount = (Account)subject.getPrincipal();
        Account account = accountService.getPwdById(curAccount.getId());
        info.addStringPermission(account.getLevel());

        return info;
    }


    //执行认证逻辑
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //取出token中的username转成int型的id然后查询数据库
        UsernamePasswordToken token1 = (UsernamePasswordToken) token;
     try {
    int id = Integer.parseInt(token1.getUsername());
        System.out.println("去数据库权限信息");
        Account account = accountService.getPwdById(id);

        if (account == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(account, account.getPassword(), getName());
         }catch(Exception e){return null;}
    }

}
