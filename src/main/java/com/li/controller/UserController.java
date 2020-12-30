package com.li.controller;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.li.annotation.TpLog;
import com.li.entity.Emp;
import com.li.entity.User;
import com.li.service.UserService;
import com.li.utils.JwtUtil;
import com.li.utils.VerifyCodeUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 知识点：@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
 * 1) 如果只是使用@RestController注解Controller，则Controller中的方法无法返回jsp页面，或者html，
 * 配置的视图解析器 InternalResourceViewResolver不起作用，返回的内容就是Return 里的内容。
 * 2) 如果需要返回到指定页面，则需要用 @Controller配合视图解析器InternalResourceViewResolver才行。
 *     如果需要返回JSON，XML或自定义mediaType内容到页面，则需要在对应的方法上加上@ResponseBody注解。
 * 3)如果需要返回JSON，XML或自定义mediaType内容到页面，则需要在对应的方法上加上@ResponseBody注解
 */
@Slf4j //log4j 打印用户信息
@RestController
@CrossOrigin //允许跨域
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    //生成验证码图片
    @GetMapping("getImage")
    public String getImageCode(HttpServletRequest request) throws IOException {
        //1.使用工具类生成验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //2.将验证码放入servletContext作用域
        request.getServletContext().setAttribute("code",code);
        //3.将图片转换成base64  注意："data:image/png/base64,"告诉前端以base64解析
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(120,60,byteArrayOutputStream,code);
        return "data:image/png;base64,"+ Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());

    }

    //用户注册
    @PostMapping("register")
    //axios以json形式传递对象 ，@RequestBody把他封装成User对象
    public Map<String,Object> register(@RequestBody User user, String code,HttpServletRequest request){
        log.info("用户信息：[{}]",user.toString());
        log.info("验证码：[{}]",code);
        //0.在作用域里拿到验证码
        Map<String,Object> map = new HashMap<>();
        //1.调用业务对象
        try {
            String key = (String)request.getServletContext().getAttribute("code");
            if(key.equalsIgnoreCase(code)){
                userService.register(user);
                map.put("state",true);
                map.put("msg","注册成功");
            }else{
                throw new RuntimeException("验证码错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","注册失败");
        }

        return map;
    }

    //用户登录
    @PostMapping("login")
    public Map<String,Object> login(@RequestBody User user){
        Map<String, Object> claims = new HashMap<>();
        log.info("用户信息[{}]",user.toString());
        Map<String, Object> map = new HashMap<>();
        try {
            User login = userService.login(user);
            map.put("state",true);
            map.put("msg","登陆成功");
            map.put("user",login);
            claims.put("uid","usertest");
            String jwt = JwtUtil.createJWT(claims, 200L);
            map.put("token",jwt);
        }catch (Exception e){
            e.printStackTrace();
            map.put("state",false);
            map.put("msg",e.getMessage());
        }
        return map;
    }
    //jwt验证
    /*
    @PostMapping("test")
    public Map<String, Object> test(HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT verify = JwtUtil.verify(token);
        String id = verify.getClaim("id").asString();
        String name = verify.getClaim("name").asString();
        log.info("用户id：{}", id);
        log.info("用户名: {}", name);
        //TODO 业务逻辑
        Map<String, Object> map = new HashMap<>();
        map.put("state", true);
        map.put("msg", "请求成功");
        return map;
    }

     */

    @PostMapping("test")
    @TpLog
    public Map<String, Object> test(HttpServletRequest request) {
        String token = request.getHeader("token");
        Claims claims = JwtUtil.verifyJwt(token);
        log.info("用户id：[{}]", JSON.toJSONString(claims));
        //TODO 业务逻辑
        Map<String, Object> map = new HashMap<>();
        map.put("state", true);
        map.put("msg", "请求成功");
        return map;
    }


}
