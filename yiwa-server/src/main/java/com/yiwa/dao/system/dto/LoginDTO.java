package com.yiwa.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录参数
 * @author Caesar Liu
 * @date 2021-03-27 22:52
 */
@ApiModel("登录参数")
@Data
public class LoginDTO implements Serializable {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码")
    private String code;
}
