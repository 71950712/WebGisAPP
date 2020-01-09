package com.rita.community;

import com.community.CommunityApplication;
import com.community.bean.Article;
import com.community.service.IArticleService;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CommunityApplication.class})
public class CommunityApplicationTests {
    @Autowired
    IArticleService articleService;
    @Test
    public void contextLoads() {
    }
    @Test
    public void arrayy(){

    }

}
