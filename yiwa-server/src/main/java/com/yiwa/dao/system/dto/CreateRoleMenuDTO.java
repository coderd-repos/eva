package com.yiwa.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yiwa
 * @date 2021-03-30 10:50
 */
@Data
@ApiModel("创建角色菜单参数")
public class CreateRoleMenuDTO {

    @ApiModelProperty(value = "角色ID", example = "1")
    private Integer roleId;

    @ApiModelProperty(value = "菜单ID集")
    private List<Integer> menuIds;

    @ApiModelProperty(value = "创建人ID", hidden = true)
    private Integer createUser;
}
