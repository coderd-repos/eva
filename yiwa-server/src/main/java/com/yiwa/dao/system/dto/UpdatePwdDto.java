package com.yiwa.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yiwa
 * @date 2021-03-31 14:12
 */
@Data
@ApiModel("修改密码参数")
public class UpdatePwdDto {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Integer userId;

    @ApiModelProperty(value = "原始密码")
    private String oldPwd;

    @ApiModelProperty(value = "新密码")
    private String newPwd;
}
