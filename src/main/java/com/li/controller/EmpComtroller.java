package com.li.controller;


import ch.qos.logback.core.util.FileUtil;
import com.li.config.TpConfig;
import com.li.entity.Emp;
import com.li.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j //log4j 打印用户信息
@RestController
@CrossOrigin //允许跨域
@RequestMapping("emp")

public class EmpComtroller {
    @Autowired
    private TpConfig tpConfig;
    @Autowired
    private EmpService empService;
    //配置文件路径
    private String realPath;

   // @Value("${photo.dir}") //在application.properties文件读取地址
   // private String realPath;
    //展示所有员工
    //try里面定义的变量与catch无法共享
    @RequestMapping("findAll")
    public List<Emp> findAll(){
        return empService.findAll();
    }

//    public Map<String,Object> findAll(){
//        Map<String,Object> map = new HashMap<>();
//        try {
//            List<Emp> all = empService.findAll();
//            map.put("emp",all);
//            map.put("msg","查找成功");
//            map.put("state",true);
//        }catch(Exception e){
//            e.printStackTrace();
//            map.put("msg","查找失败");
//            map.put("state",false);
//            map.put("msg",e.getMessage());
//        }
//        return map;
//    }
    //保存员工信息和头像
    @RequestMapping("save")
    //前后端变量名要匹配
    public Map<String,Object> save(MultipartFile photo,Emp emp){
        Map<String,Object> map = new HashMap<>();
        try {
            log.info("用户信息[{}]",emp.toString());
            log.info("文件[{}]",photo.getOriginalFilename());
            //新文件名
            String newFileName = UUID.randomUUID().toString()+'.'+ FilenameUtils.getExtension(photo.getOriginalFilename());
            //通过MultipartFile的transferTo(File dest)这个方法来转存文件到指定的路径。
            //把photos文件名设置为为newFileName并转存到realPath路径下
            realPath = tpConfig.getFileDir();
            photo.transferTo(new File(realPath,newFileName));
            //设置头像地址,因为上传的路径在static下 可通过项目名ems_vue＋文件名去访问 ems_vue由前端拼接
            emp.setPath(newFileName);
            empService.save(emp);
            map.put("state",true);
            map.put("msg","保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state",false);
            map.put("msg",e.getMessage());
        }
        return map;
    }
}
