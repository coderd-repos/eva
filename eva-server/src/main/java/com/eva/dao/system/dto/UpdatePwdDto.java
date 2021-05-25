package com.eva.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Caesar Liu
 * @date 2021-03-31 14:12
 */
@Data
@ApiModel("修改密码参数")
public class UpdatePwdDto {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Integer userId;

    @NotBlank(message = "原始密码不能为空")
    @ApiModelProperty(value = "原始密码")
    private String oldPwd;

    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码")
    private String newPwd;
}
