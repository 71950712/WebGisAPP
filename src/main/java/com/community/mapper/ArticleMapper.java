package com.community.mapper;

import com.community.bean.Account;
import com.community.bean.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ArticleMapper {
    @Select("select * from article limit 1")
    Article selectOne();
    List<Article> findAll(@Param("pageNo") int pageNo,@Param("limit") int limit,@Param("title") String title,@Param("author") String author);
    int countTotal(@Param("title") String title,@Param("author") String author);
    @Select("select id,title,author,content,createTime from article where id = #{id} ")
    Article findArticleById(int id);

}
