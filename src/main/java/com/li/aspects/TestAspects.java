package com.li.aspects;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class TestAspects {
    //可以用annotation，exculede
    //匿名切点，即直接在增强操作方法里直接写切点表达式
    //@Before("within(com.li.service.Impl.*ServiceImpl)")
    //@Before("execution(* com.baizhi.service.*.*(..))")
    public void before(JoinPoint joinPoint){
        System.out.println("前置通知------------------------------------------");
        System.out.println("目标方法名"+joinPoint.getSignature().getName());
        System.out.println("目标参数名"+ JSON.toJSONString(joinPoint.getArgs()));
        System.out.println("目标对象"+joinPoint.getTarget());
    }

    //环绕通知  当目标方法执行时会先进入环绕通知，然后再环绕通知放行之后进入目标方法，然后执行目标方法，目标方法执行完之后回到环绕通知
    //@Around("within(com.li.service.Impl.*ServiceImpl)")
    public Object arrount(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("进入环绕通知----------");
        System.out.println(proceedingJoinPoint.getSignature().getName());
        Object proceed = proceedingJoinPoint.proceed();//放行环绕通知
        System.out.println("业务方法执行后环绕通知----------");
        System.out.println(proceed);
        return proceed;
    }
}
