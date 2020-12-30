package com.li.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//获取HttpServletRequest对象拿到请求参数用
public class HttpContextUtils {
    public static HttpServletRequest getHttpServletRequest() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        } else {
            return null;
        }
    }
}
