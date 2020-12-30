package com.li.dao;

import com.li.entity.Emp;
import com.li.entity.User;
import com.li.entity.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

//@Repository("Bean的名称") 定义DAO层Bean
// @Repository需要在Spring中配置扫描地址，然后生成Dao层的Bean才能被注入到Service层中。
// @Mapper不需要配置扫描地址，通过xml里面的namespace里面的接口地址，生成了Bean后注入到Service层中。
@Mapper
public interface UserDao {
    User findByUsername(String userName);
    void insertUser(User u);
}
