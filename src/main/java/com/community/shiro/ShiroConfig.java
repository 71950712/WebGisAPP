package com.community.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;


import org.apache.shiro.codec.Base64;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    //获取application.properties参数,此处不能加static关键字
    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String redisPassword;


    /**
     * Shiro生命周期处理器
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
       //"===============(1)Shiro生命周期周期处理器设置");
        return new LifecycleBeanPostProcessor();
    }

    /**
       *设置sessionid的cookie
     */
    @Bean
    public SimpleCookie sessionIdCookie(){
        SimpleCookie sessionIdCookie=new SimpleCookie("sessionID");
        sessionIdCookie.setMaxAge(-1);
        sessionIdCookie.setHttpOnly(true);
        return sessionIdCookie;
    }



    /**
     * cookie对象;
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(150);
        return simpleCookie;
    }
    /**
     * cookie管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        //System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
       ShiroFilterFactoryBean shiroFilterFactoryBean= new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setSuccessUrl("/account/ts");
        shiroFilterFactoryBean.setLoginUrl("/login");// 被拦截后跳转路径
        shiroFilterFactoryBean.setUnauthorizedUrl("/");
        Map<String,String> filterMap = new LinkedHashMap<String,String>();
        /*shiro内置过滤器可拦截请求
        anon:无需登录
        authc:必须登录后可访问
        user:若使用rememberMe可访问
        perms:必需得到资源权限才能访问
        role:得到角色权限才能访问
         */
        //filterMap.put("/teaf","anon");
        filterMap.put("/","anon");
        filterMap.put("/login","anon");
        filterMap.put("/register","anon");
        //filterMap.put("/", "logout");
        filterMap.put("account/add","perms[2]");

        //filterMap.put("/**","authc");//拦截的映射,/add/*拦截所有路径下的
        //user表示通过验证或记住我两种方式都可以访问
        filterMap.put("/**","user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }



    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("accountRealm")AccountRealm accountRealm) {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(accountRealm);
        defaultSecurityManager.setRememberMeManager(rememberMeManager());
        defaultSecurityManager.setCacheManager(redisCacheManager());
        defaultSecurityManager.setSessionManager(defaultWebSessionManager());

        return defaultSecurityManager;
    }

    @Bean
    public AccountRealm accountRealm(){
         AccountRealm accountRealm = new AccountRealm();
         accountRealm.setCachingEnabled(true);
        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        accountRealm.setAuthenticationCachingEnabled(true);
        //缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
        accountRealm.setAuthenticationCacheName("authenticationCache");
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
        accountRealm.setAuthorizationCachingEnabled(true);
        //缓存AuthorizationInfo信息的缓存名称  在ehcache-shiro.xml中有对应缓存的配置
        accountRealm.setAuthorizationCacheName("authorizationCache");
        //根据情况使用缓存器
        accountRealm.setCacheManager(redisCacheManager());

         return accountRealm;
    }
//整合thymeleaf和shiro
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        //"===============(3)创建缓存管理器RedisCacheManager");

        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
        redisCacheManager.setPrincipalIdFieldName("id");
        //用户权限信息缓存时间
        redisCacheManager.setExpire(300);
        return redisCacheManager;
    }
    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        //"===============(4)创建RedisManager,连接Redis..URL= " + host + ":" + port);
        RedisManager redisManager = new RedisManager();
        int port1 = Integer.parseInt(port);
        redisManager.setHost(host);
        redisManager.setPort(port1);
        redisManager.setPassword(redisPassword);

        return redisManager;
    }


    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
     @Bean
     public DefaultWebSessionManager defaultWebSessionManager(){
         DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
         sessionManager.setSessionDAO(redisSessionDAO());
         sessionManager.setGlobalSessionTimeout(150*1000);
         sessionManager.setCacheManager(redisCacheManager());
         sessionManager.setDeleteInvalidSessions(true);
         sessionManager.setSessionIdCookieEnabled(true);
         sessionManager.setSessionIdCookie(sessionIdCookie());
         return sessionManager;
     }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }





}
