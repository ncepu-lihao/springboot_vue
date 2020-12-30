package com.li.service.Impl;

import com.li.annotation.TpLog;
import com.li.dao.UserDao;
import com.li.entity.Emp;
import com.li.entity.User;
import com.li.entity.UserLogin;
import com.li.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public void register(User user) {
        //1.生成用户状态
        user.setStatus("已激活");
        //2.生成用户注册时间
        user.setRegsterTime(new Date());
        //3.调用dao
        userDao.insertUser(user);

    }

    @Override
    @TpLog
    public User login(User user) {
        User byUsername = userDao.findByUsername(user.getUsername());
        if (!ObjectUtils.isEmpty(byUsername)){
            if(byUsername.getPassword().equals(user.getPassword())){
                    return byUsername;
            }else{
                throw new RuntimeException("密码不正确");
            }
        }else {
            throw new RuntimeException("用户名不存在");
        }

    }


}
