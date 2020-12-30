package com.li.Interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.li.entity.vo.Result;
import com.li.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.InvalidClaimException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    //原项目写法 返回异常类型不明确只有token无效
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Claims claims = JwtUtil.verifyJwt(token);
        if (claims == null) {
            Result result = new Result(-101, "token无效", " ");
            String reStr = JSON.toJSONString(result);
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(reStr);
            return false;
        }
        return true;
    }

    /*
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Map<String,Object> map = new HashMap<>();
        try {
            DecodedJWT verify = JwtUtil.verify(token);
            return  true;
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg", "签名不一致");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("msg", "令牌过期");
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            map.put("msg", "算法不匹配");
        } catch (InvalidClaimException e) {
            e.printStackTrace();
            map.put("msg", "失效的payload");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "token无效");
        }
        map.put("state", false);
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(map));
        return  false;
    }

     */
    }
