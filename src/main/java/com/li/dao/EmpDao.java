package com.li.dao;

import com.li.entity.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpDao {
    List<Emp> findAll();
    //增加员工
    void save(Emp emp);
    //通过id查找员工信息
    Emp findById(Integer id);
}
