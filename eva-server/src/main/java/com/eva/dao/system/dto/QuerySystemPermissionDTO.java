package com.eva.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Eva
 * @date 2021-03-31 15:07
 */
@Data
@ApiModel("查询系统权限参数")
public class QuerySystemPermissionDTO implements Serializable {

    @ApiModelProperty(value = "权限编码")
    private String code;

    @ApiModelProperty(value = "权限名称")
    private String name;

}
