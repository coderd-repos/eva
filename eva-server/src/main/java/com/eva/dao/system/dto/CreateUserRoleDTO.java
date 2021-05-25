package com.eva.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Caesar Liu
 * @date 2021-03-29 22:24
 */
@Data
@ApiModel("创建用户角色参数")
public class CreateUserRoleDTO {

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    @ApiModelProperty(value = "角色ID集")
    @NotNull(message = "角色ID集不能为空")
    private List<Integer> roleIds;

    @ApiModelProperty(value = "创建人", hidden = true)
    private Integer createUser;
}
