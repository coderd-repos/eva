package com.yiwa.biz.system.impl;

import com.yiwa.biz.system.SystemRoleBiz;
import com.yiwa.dao.system.dto.CreateRoleMenuDTO;
import com.yiwa.dao.system.dto.CreateRolePermissionDTO;
import com.yiwa.dao.system.model.SystemRoleMenu;
import com.yiwa.dao.system.model.SystemRolePermission;
import com.yiwa.service.system.SystemRoleMenuService;
import com.yiwa.service.system.SystemRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemRoleBizImpl implements SystemRoleBiz {

    @Autowired
    private SystemRolePermissionService systemRolePermissionService;

    @Autowired
    private SystemRoleMenuService systemRoleMenuService;

    @Override
    public void createRolePermission(CreateRolePermissionDTO dto) {
        // 删除关联权限
        SystemRolePermission deleteDto = new SystemRolePermission();
        deleteDto.setRoleId(dto.getRoleId());
        systemRolePermissionService.delete(deleteDto);
        // 新增新的权限
        for (Integer permissionId : dto.getPermissionIds()) {
            SystemRolePermission newRolePermission = new SystemRolePermission();
            newRolePermission.setRoleId(dto.getRoleId());
            newRolePermission.setPermissionId(permissionId);
            systemRolePermissionService.create(newRolePermission);
        }
    }

    @Override
    public void createRoleMenu(CreateRoleMenuDTO dto) {
        // 删除关联权限
        SystemRoleMenu deleteDto = new SystemRoleMenu();
        deleteDto.setRoleId(dto.getRoleId());
        systemRoleMenuService.delete(deleteDto);
        // 新增新的权限
        for (Integer menuId : dto.getMenuIds()) {
            SystemRoleMenu newRoleMenu = new SystemRoleMenu();
            newRoleMenu.setRoleId(dto.getRoleId());
            newRoleMenu.setMenuId(menuId);
            systemRoleMenuService.create(newRoleMenu);
        }
    }
}
