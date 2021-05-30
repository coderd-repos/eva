package com.eva.biz.system.impl;

import com.eva.biz.system.SystemRoleBiz;
import com.eva.core.exception.BusinessException;
import com.eva.core.model.ResponseStatus;
import com.eva.dao.system.dto.CreateRoleMenuDTO;
import com.eva.dao.system.dto.CreateRolePermissionDTO;
import com.eva.dao.system.model.SystemRole;
import com.eva.dao.system.model.SystemRoleMenu;
import com.eva.dao.system.model.SystemRolePermission;
import com.eva.service.system.SystemRoleMenuService;
import com.eva.service.system.SystemRolePermissionService;
import com.eva.service.system.SystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SystemRoleBizImpl implements SystemRoleBiz {

    @Autowired
    private SystemRoleService systemRoleService;

    @Autowired
    private SystemRolePermissionService systemRolePermissionService;

    @Autowired
    private SystemRoleMenuService systemRoleMenuService;

    @Override
    public Integer create(SystemRole systemRole) {
        // 验证用户名
        SystemRole queryDto = new SystemRole();
        queryDto.setCode(systemRole.getCode());
        queryDto.setDeleted(Boolean.FALSE);
        SystemRole role = systemRoleService.findOne(queryDto);
        if (role != null) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "角色编码已存在");
        }
        return systemRoleService.create(systemRole);
    }

    @Override
    public void updateById(SystemRole systemRole) {
        // 验证角色编码
        SystemRole queryDto = new SystemRole();
        queryDto.setCode(systemRole.getCode());
        queryDto.setDeleted(Boolean.FALSE);
        SystemRole role = systemRoleService.findOne(queryDto);
        if (role != null && !role.getId().equals(systemRole.getId())) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "角色编号已存在");
        }
        systemRoleService.updateById(systemRole);
    }

    @Override
    public void deleteById(Integer id) {
        SystemRole role = systemRoleService.findById(id);
        if (role == null) {
            return;
        }
        if (role.getFixed()) {
            throw new BusinessException(ResponseStatus.NOT_ALLOWED.getCode(), "请勿删除" + role.getName() + "，因为这是固定角色");
        }
        systemRoleService.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

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
