package com.yiwa.dao.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录参数
 * @author Caesar Liu
 * @date 2021-03-27 22:52
 */
@Data
public class LoginDTO implements Serializable {

    private String username;

    private String password;
}
