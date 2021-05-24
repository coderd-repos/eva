package com.eva.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Eva
 * @date 2021-03-31 20:00
 */
@Data
@ApiModel("重置用户密码参数对象")
public class ResetSystemUserPwdDTO {

    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @ApiModelProperty(value = "新密码")
    private String password;

    @ApiModelProperty(value = "操作人", hidden = true)
    private Integer operaUserId;
}
