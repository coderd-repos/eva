package com.yiwa.dao.system.dto;

import com.yiwa.dao.system.model.SystemUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 创建用户参数对象
 * @author Yiwa
 * @date 2021-05-22 11:53
 */
@Data
public class CreateSystemUserDTO extends SystemUser {

    @ApiModelProperty(value = "岗位ID", example = "1")
    private Integer positionId;

    @ApiModelProperty(value = "部门ID", example = "1")
    private Integer departmentId;
}
