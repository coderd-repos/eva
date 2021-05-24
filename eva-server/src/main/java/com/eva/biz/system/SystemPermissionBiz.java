package com.eva.biz.system;

import com.eva.dao.system.model.SystemPermission;

/**
 * 系统权限业务处理
 * @author Eva
 * @date 2021-05-21 15:45
 */
public interface SystemPermissionBiz {

    /**
     * 创建
     * @author Eva
     * @date 2021-05-21 15:45
     */
    Integer create(SystemPermission systemPermission);

    /**
     * 修改
     * @author Eva
     * @date 2021-05-21 15:48
     */
    void updateById(SystemPermission systemPermission);
}
