package com.li.service;

import com.li.dao.UserDao;
import com.li.entity.Emp;
import com.li.entity.User;
import com.li.entity.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public interface UserService {
    //用户注册
    void register(User user);
    //用户登录验证
    User login(User user);


}
