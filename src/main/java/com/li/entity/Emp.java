package com.li.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
//放入redis中的对象要实现序列化
public class Emp implements Serializable {
    private String id;
    private String name;
    private String path;
    private Double salary;
    private String age;
}
