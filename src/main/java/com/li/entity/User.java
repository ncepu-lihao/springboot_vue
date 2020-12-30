package com.li.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 * https://blog.csdn.net/weixin_38229356/article/details/82937420
 * @Data ： 注在类上，提供类的get、set、equals、hashCode、canEqual、toString方法
 * @AllArgsConstructor ： 注在类上，提供类的全参构造
 * @NoArgsConstructor ： 注在类上，提供类的无参构造
 * @Setter ： 注在属性上，提供 set 方法
 * @Getter ： 注在属性上，提供 get 方法
 * @EqualsAndHashCode ： 注在类上，提供对应的 equals 和 hashCode 方法
 * @Log4j/@Slf4j ： 注在类上，提供对应的 Logger 对象，变量名为 log
 *@Accessors用于配置getter和setter方法的生成结果
 *  1.chain
 * chain的中文含义是链式的，设置为true，则setter方法返回当前对象。
 *  2.prefix
 * prefix的中文含义是前缀，用于生成getter和setter方法的字段名会忽视指定前缀（遵守驼峰命名）。
 *  3.fluent
 * fluent的中文含义是流畅的，设置为true，则getter和setter方法的方法名都是基础属性名，且setter方法返回当前对象。
 */
@Data
@Accessors(chain = true)
public class User {
    private String id;  //string类型api较多
    private String username;
    private String realname;
    private String password;
    private String sex;
    private String status;
    private Date regsterTime;
}
