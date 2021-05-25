package com.eva.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Eva
 * @date 2021-03-30 10:50
 */
@Data
@ApiModel("创建角色权限参数")
public class CreateRolePermissionDTO {

    @ApiModelProperty(value = "角色ID")
    @NotNull(message = "角色ID不能为空")
    private Integer roleId;

    @ApiModelProperty(value = "权限ID集")
    @NotNull(message = "权限ID集不能为空")
    private List<Integer> permissionIds;

    @ApiModelProperty(value = "创建人", hidden = true)
    private Integer createUser;
}
