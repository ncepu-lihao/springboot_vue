package com.li.service.Impl;

import com.li.dao.EmpDao;
import com.li.entity.Emp;
import com.li.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpDao empDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)   //支持事务，查询没必要使用事务
    public List<Emp> findAll() {
        return empDao.findAll();
    }

    @Override
    public void save(Emp emp) {
        empDao.save(emp);
    }

    @Override
    public Emp findById(Integer id) {
        return empDao.findById(id);
    }
}
