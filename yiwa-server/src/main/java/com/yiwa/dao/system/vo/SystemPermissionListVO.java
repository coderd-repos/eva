package com.yiwa.dao.system.vo;

import com.yiwa.dao.system.model.SystemPermission;
import com.yiwa.dao.system.model.SystemUser;
import lombok.Data;

/**
 * 系统权限列表视图对象
 * @author Yiwa
 * @date 2021-03-31 15:09
 */
@Data
public class SystemPermissionListVO extends SystemPermission {

    private SystemUser createUserInfo;

    private SystemUser updateUserInfo;
}
