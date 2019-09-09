package com.community.controller;

import com.community.bean.Article;
import com.community.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArticleController {
    @Autowired
    private IArticleService articleService;
    @RequestMapping(path="/account/article")
    public void getArticle(){

         Article article = articleService.selectOne();
         System.out.println(article);
    }
}
