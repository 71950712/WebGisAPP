package com.community.mapper;

import com.community.bean.Account;
import com.community.bean.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;



@Mapper
public interface ArticleMapper {
    @Select("select * from article limit 1")
    Article selectOne();
    Account selectPwdById(int id);


}
