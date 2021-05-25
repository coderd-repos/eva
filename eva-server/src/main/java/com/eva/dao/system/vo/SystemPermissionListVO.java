package com.eva.dao.system.vo;

import com.eva.dao.system.model.SystemPermission;
import com.eva.dao.system.model.SystemUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Eva
 * @date 2021-03-31 15:09
 */
@Data
@ApiModel("系统权限列表视图对象")
public class SystemPermissionListVO extends SystemPermission {

    @ApiModelProperty(value = "创建人信息")
    private SystemUser createUserInfo;

    @ApiModelProperty(value = "更新人信息")
    private SystemUser updateUserInfo;
}
