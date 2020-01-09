package com.community.service;

import com.community.bean.Article;

import java.util.List;

public interface IArticleService {
    Article selectOne();
    List<Article> findAll(int pageNo, int limit, String title, String author);
    int countTotal(String title, String author);
    Article findArticleById(int id);
}
