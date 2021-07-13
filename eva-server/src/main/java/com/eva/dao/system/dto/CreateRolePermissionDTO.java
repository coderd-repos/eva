package com.eva.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Data
@ApiModel("创建角色权限参数")
public class CreateRolePermissionDTO implements Serializable {

    @ApiModelProperty(value = "角色ID")
    @NotNull(message = "角色ID不能为空")
    private Integer roleId;

    @ApiModelProperty(value = "权限ID集")
    @NotNull(message = "权限ID集不能为空")
    private List<Integer> permissionIds;

    @ApiModelProperty(value = "创建人", hidden = true)
    private Integer createUser;
}
