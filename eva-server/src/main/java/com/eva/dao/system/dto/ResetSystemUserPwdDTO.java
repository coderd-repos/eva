package com.eva.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Data
@ApiModel("重置用户密码参数对象")
public class ResetSystemUserPwdDTO implements Serializable {

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Integer id;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "新密码不能为空")
    private String password;

    @ApiModelProperty(value = "操作人", hidden = true)
    private Integer operaUserId;
}
