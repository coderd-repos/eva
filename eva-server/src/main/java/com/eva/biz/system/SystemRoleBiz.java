package com.eva.biz.system;

import com.eva.dao.system.dto.CreateRoleMenuDTO;
import com.eva.dao.system.dto.CreateRolePermissionDTO;

/**
 * 角色权限业务处理
 * @author Eva
 * @date 2021-03-30 10:51
 */
public interface SystemRoleBiz {

    /**
     * 创建角色权限
     * @author Eva
     * @date 2021-03-30 10:51
     */
    void createRolePermission(CreateRolePermissionDTO dto);

    /**
     * 创建角色菜单
     * @author Eva
     * @date 2021-03-30 10:51
     */
    void createRoleMenu(CreateRoleMenuDTO dto);
}
