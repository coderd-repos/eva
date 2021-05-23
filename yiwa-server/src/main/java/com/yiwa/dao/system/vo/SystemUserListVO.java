package com.yiwa.dao.system.vo;

import com.yiwa.dao.system.model.SystemDepartment;
import com.yiwa.dao.system.model.SystemPosition;
import com.yiwa.dao.system.model.SystemRole;
import com.yiwa.dao.system.model.SystemUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yiwa
 * @date 2021-03-29 22:47
 */
@Data
@ApiModel("系统用户列表视图对象")
public class SystemUserListVO extends SystemUser {

    @ApiModelProperty(value = "角色")
    private List<SystemRole> roles;

    @ApiModelProperty(value = "岗位信息")
    private SystemPosition position;

    @ApiModelProperty(value = "部门信息")
    private SystemDepartment department;

    @ApiModelProperty(value = "创建人信息")
    private SystemUser createUserInfo;

    @ApiModelProperty(value = "更新人信息")
    private SystemUser updateUserInfo;

}
