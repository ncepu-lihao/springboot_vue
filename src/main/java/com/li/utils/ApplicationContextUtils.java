package com.li.utils;

import jdk.dynalink.beans.StaticClass;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//ApplicationContextAware相关知识
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }
    //获取redistemplate
    public static Object getApplicationContext(String beanId){
        return  applicationContext.getBean(beanId);
    }
}
