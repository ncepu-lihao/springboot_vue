package com.li.annotation;


import java.lang.annotation.*;

/**
 * 自定义日志注解
 */
@Target({ElementType.METHOD,ElementType.TYPE}) //注解作用范围ElementType.METHOD方法ElementType.TYPE类
@Retention(RetentionPolicy.RUNTIME)
@Documented //注释文档
public @interface TpLog {
    //value值存的是日志的含义
    String value() default "";
}
