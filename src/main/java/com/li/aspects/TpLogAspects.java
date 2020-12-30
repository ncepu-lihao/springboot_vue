package com.li.aspects;

import com.alibaba.fastjson.JSON;
import com.li.dao.LogDao;
import com.li.entity.modeldo.LogDo;
import com.li.utils.HttpContextUtils;
import com.li.utils.IPUtils;
import com.li.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.*;

//日志切面
@Aspect
@Configuration
@SuppressWarnings("unchecked")
public class TpLogAspects {
    @Autowired
    private LogDo logDO;
    @Autowired
    private LogDao logDao;


    //Pointcut表示往哪里去切，这里有一个注解，
    // 表示要拦截标注有@Log这种注解的信息，然后进行操作
    @Pointcut("@annotation(com.li.annotation.TpLog)")
    public void logPointCut() {
        System.out.println("这是一个切入点");
    }

    //定义完切点以后,对切点进行增强操作,环绕增强
    //使用前面定义切点的方法名,这种方式属于独立切点命名
    //将切点单独定义出来，对于当切点较多时，能够提高一些重用性。这种算是显式地定义切点
    @Around("logPointCut()")
    public Object tpLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        //执行时长（ms）
        long time = System.currentTimeMillis()-begin;
        saveLog(proceedingJoinPoint,time);
        return  proceed;
    }
    public void saveLog(ProceedingJoinPoint proceedingJoinPoint,long time){
        Signature signature = proceedingJoinPoint.getSignature();
        //请求方法名
        String functionName2 = signature.getName();
        // 请求的方法名
        String functionName = proceedingJoinPoint.getTarget().getClass().getName();
        //请求类名
        String className = proceedingJoinPoint.getClass().getName();

        //请求参数
        Object[] requrieArgs = proceedingJoinPoint.getArgs();
        //fastjosn解析object类型的数组不是如下方式
        //requrieArg = JSON.toJSONString(requrieArgs);
        String requrieArg = Arrays.toString(requrieArgs);
        String body = className+"|||"+functionName+"|||"+functionName2+"|||"+"args"+requrieArg;
        logDO.setBody(body);
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String params = null;
        try {
            if(request!=null) {
                params = JSON.toJSONString(getAllRequestParam(request));
            }

        } catch (Exception e) {
        }

        String token =null;
        if(params!=null){
            logDO.setParams(params);
            // 设置IP地址
            logDO.setIp(IPUtils.getIpAddr(request));

            token = request.getHeader("token"); //获取请求传来的token
            logDO.setToken(token);

        }else {
            logDO.setIp("-1");
            logDO.setParams(null);
        }

        Claims claims = null;

        if (token != null) {
            claims = JwtUtil.verifyJwt(token); //验证token
        }

        if (claims != null) {

            logDO.setUsername((String) claims.get("user"));
        } else {
            logDO.setUsername("获取用户信息为空");
        }
        //方法执行时间
        logDO.setCostTime((int) time);
        // 系统当前时间
        Date date = new Date();
        logDO.setCreateDate(date);
        //数据库表jade_log中的create_date的类型必须改为datetime
        // 保存系统日志
        logDao.save(logDO);
    }




    /**
     * 获取客户端请求参数中所有的信息
     *
     * @param request
     * @return
     */
    private static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>(1);
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                //如果字段的值为空，判断若值为空，则删除这个字段>
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }
    

}
