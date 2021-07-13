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
@ApiModel("创建角色菜单参数")
public class CreateRoleMenuDTO implements Serializable {

    @ApiModelProperty(value = "角色ID", example = "1")
    @NotNull(message = "角色ID不能为空")
    private Integer roleId;

    @ApiModelProperty(value = "菜单ID集")
    @NotNull(message = "菜单ID集不能为空")
    private List<Integer> menuIds;

    @ApiModelProperty(value = "创建人ID", hidden = true)
    private Integer createUser;
}
