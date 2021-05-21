package com.yiwa.biz.system.impl;

import com.yiwa.biz.system.SystemPermissionBiz;
import com.yiwa.core.model.BusinessException;
import com.yiwa.core.model.ResponseStatus;
import com.yiwa.dao.system.model.SystemPermission;
import com.yiwa.service.system.SystemPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemPermissionBizImpl implements SystemPermissionBiz {

    @Autowired
    private SystemPermissionService systemPermissionService;

    @Override
    public Integer create(SystemPermission systemPermission) {
        SystemPermission queryDto = new SystemPermission();
        queryDto.setCode(systemPermission.getCode());
        queryDto.setDeleted(Boolean.TRUE);
        SystemPermission permission = systemPermissionService.findOne(queryDto);
        if (permission != null) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "权限编码已存在");
        }
        return systemPermissionService.create(systemPermission);
    }

    @Override
    public void updateById(SystemPermission systemPermission) {
        SystemPermission queryDto = new SystemPermission();
        queryDto.setCode(systemPermission.getCode());
        queryDto.setDeleted(Boolean.TRUE);
        SystemPermission permission = systemPermissionService.findOne(queryDto);
        if (permission != null && !systemPermission.getId().equals(permission.getId())) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "权限编码已存在");
        }
        systemPermissionService.updateById(systemPermission);
    }
}
