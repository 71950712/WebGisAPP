package com.community.service.impl;

import com.community.bean.Article;
import com.community.mapper.AccountMapper;
import com.community.mapper.ArticleMapper;
import com.community.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Cacheable(key="'article_list'+#pageNo+#limit")
   public List<Article> findAll(int pageNo, int limit, String title, String author){
        pageNo = pageNo - 1;
        pageNo = pageNo * limit;       
        return articleMapper.findAll(pageNo,limit,title,author);

   }

   public int countTotal(String title, String author){
        return articleMapper.countTotal(title,author);
   }
   @Cacheable(key="'article_id'+#id")
   public Article findArticleById(int id){
	   return articleMapper.findArticleById(id);
   }
}
