package com.community.mapper;

import com.community.bean.Account;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.persistence.criteria.CriteriaBuilder;
import javax.security.auth.login.AccountException;
import java.util.List;

@Mapper
public interface AccountMapper {
    @Select("select * from account where id = #{id}")
    List<Account> selectById(@Param("id") int id);
    Account selectPwdById(int id);
    int insertOne(int id,String pwd);

}
