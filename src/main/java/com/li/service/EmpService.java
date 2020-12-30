package com.li.service;

import com.li.entity.Emp;

import java.util.List;

public interface EmpService {
    //展示所有员工
    List<Emp> findAll();
    //添加员工
    void save(Emp emp);
    //根据id查找员工
    Emp findById(Integer id);
}
