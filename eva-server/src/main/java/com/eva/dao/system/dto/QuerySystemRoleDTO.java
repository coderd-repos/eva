package com.eva.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Eva
 * @date 2021-03-30 11:28
 */
@Data
@ApiModel("查询系统角色参数")
public class QuerySystemRoleDTO {

    @ApiModelProperty(value = "角色编码")
    private String code;

    @ApiModelProperty(value = "角色名称")
    private String name;
}