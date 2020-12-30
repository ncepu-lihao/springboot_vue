package com.li.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserLogin {
    private String username;
    private String password;
}
