package com.li.controller;

import com.alibaba.fastjson.JSON;
import com.li.entity.Emp;
import com.li.entity.vo.Result;
import com.li.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping("test")
public class InterfaceTestController {
    @Autowired
    private EmpService empService;
    @GetMapping("result")
    public Result findEmpById(Integer id){
        try {
            Emp emp = empService.findById(id);
            String s = JSON.toJSONString(emp);
            Result success = Result.success(s);
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            Result fail = Result.fail();
            return fail;
        }
    }

}
