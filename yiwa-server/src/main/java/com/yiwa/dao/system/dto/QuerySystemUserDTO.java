package com.yiwa.dao.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 查询系统用户参数
 * @author Yiwa
 * @date 2021-03-29 22:46
 */
@Data
public class QuerySystemUserDTO {

    @ApiModelProperty("用于查询子部门的部门ID")
    private Integer rootDeptId;

    @ApiModelProperty("精准匹配的部门ID")
    private Integer strictDeptId;

    @ApiModelProperty(value = "部门ID集", hidden = true)
    private List<Integer> departmentIds;

    @ApiModelProperty("岗位ID")
    private Integer positionId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("姓名")
    private String realname;

    @ApiModelProperty("手机号码")
    private String mobile;
}
