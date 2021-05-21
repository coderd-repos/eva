package com.yiwa.biz.system;

import com.yiwa.dao.system.dto.CreateRoleMenuDTO;
import com.yiwa.dao.system.dto.CreateRolePermissionDTO;

/**
 * 角色权限业务处理
 * @author Yiwa
 * @date 2021-03-30 10:51
 */
public interface SystemRoleBiz {

    /**
     * 创建角色权限
     * @author Yiwa
     * @date 2021-03-30 10:51
     */
    void createRolePermission(CreateRolePermissionDTO dto);

    /**
     * 创建角色菜单
     * @author Yiwa
     * @date 2021-03-30 10:51
     */
    void createRoleMenu(CreateRoleMenuDTO dto);
}
