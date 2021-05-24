package com.eva.dao.system.dto;

import com.eva.dao.system.model.SystemUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Eva
 * @date 2021-05-22 11:53
 */
@Data
@ApiModel("创建用户参数")
public class CreateSystemUserDTO extends SystemUser {

    @ApiModelProperty(value = "岗位ID", example = "1")
    private Integer positionId;

    @ApiModelProperty(value = "部门ID", example = "1")
    private Integer departmentId;
}