package com.community.controller;

import com.community.bean.Article;
import com.community.bean.PageHelper;
import com.community.service.IArticleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ArticleController {
    @Autowired
    private IArticleService articleService;
    @RequestMapping(path="/account/articleList")
    @ResponseBody
    public PageHelper<Article> getArticle(@RequestParam("page") int page, @RequestParam("limit") int limit,@RequestParam("title") String title,@RequestParam("author") String author){
        PageHelper<Article> pageHelper = new PageHelper<Article>();
        System.out.println("page"+page);
        System.out.println("limit"+limit);
        System.out.println("title"+title);
        int total = articleService.countTotal(title,author);
        List<Article> article = articleService.findAll(page,limit,title,author);
        pageHelper.setTotal(total);
        pageHelper.setRows(article);
         return pageHelper;
    }
/*
    @RequestMapping(path="/account/article1")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> news(@RequestParam("page") int page){
        Map<String, Object> map = new HashMap<String, Object>();
        int pageSize = 4;
        int total = articleService.countTotal();
        int totalPage = total / pageSize;// 计算总页数

        if (total % pageSize != 0) {// 不满一页的数据按一页计算
            totalPage++;
        }
        if (page > totalPage)// 当页数大于总页数，直接返回
            return null;
        List<Article> article = articleService.findAll(page,pageSize);
        System.out.println(article.toString());
        map.put("page", page);
        map.put("pageSize", pageSize);
        map.put("totalPage", totalPage);
        map.put("list",article);

        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }
    */
    
    @RequestMapping(path="/account/details/{id}")
    public String getArticle(@PathVariable("id") int id,Model m){

    	Article article = articleService.findArticleById(id);
    	System.out.println(article);
    	m.addAttribute("article", article);
    	return "account/articleDetail";
    }

}
