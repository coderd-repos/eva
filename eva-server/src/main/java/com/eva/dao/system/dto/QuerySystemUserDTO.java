package com.eva.dao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Eva
 * @date 2021-03-29 22:46
 */
@Data
@ApiModel("查询系统用户参数")
public class QuerySystemUserDTO implements Serializable {

    @ApiModelProperty(value = "用于查询子部门的部门ID")
    private Integer rootDeptId;

    @ApiModelProperty(value = "精准匹配的部门ID")
    private Integer strictDeptId;

    @ApiModelProperty(value = "部门ID集", hidden = true)
    private List<Integer> departmentIds;

    @ApiModelProperty(value = "岗位ID")
    private Integer positionId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "姓名")
    private String realname;

    @ApiModelProperty(value = "手机号码")
    private String mobile;
}
