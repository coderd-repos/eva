package com.yiwa.biz.system;

import com.yiwa.dao.system.model.SystemPermission;

/**
 * 系统权限业务处理
 * @author Yiwa
 * @date 2021-05-21 15:45
 */
public interface SystemPermissionBiz {

    /**
     * 创建
     * @author Yiwa
     * @date 2021-05-21 15:45
     */
    Integer create(SystemPermission systemPermission);

    /**
     * 修改
     * @author Yiwa
     * @date 2021-05-21 15:48
     */
    void updateById(SystemPermission systemPermission);
}
