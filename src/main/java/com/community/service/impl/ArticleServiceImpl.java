package com.community.service.impl;

import com.community.bean.Article;
import com.community.mapper.AccountMapper;
import com.community.mapper.ArticleMapper;
import com.community.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "articleCache")
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Cacheable(key="'id_'+#id")
   public Article selectOne() {
       try {
           return articleMapper.selectOne();
       }catch (Exception e){
           return null;
       }finally {
           System.out.println("查询了mysql数据库");
       }

   }
}
