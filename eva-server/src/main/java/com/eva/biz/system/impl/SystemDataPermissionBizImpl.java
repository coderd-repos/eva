package com.eva.biz.system.impl;

import com.eva.biz.system.SystemDataPermissionBiz;
import com.eva.core.constants.DataPermissionConstants;
import com.eva.core.constants.ResponseStatus;
import com.eva.core.exception.BusinessException;
import com.eva.dao.system.model.SystemDataPermission;
import com.eva.service.system.SystemDataPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemDataPermissionBizImpl implements SystemDataPermissionBiz {

    @Autowired
    private SystemDataPermissionService systemDataPermissionService;

    @Override
    public Integer create(SystemDataPermission systemDataPermission) {
        // 验证是否已存在配置
        this.check(systemDataPermission, "该业务模块与角色存在有效的数据权限，请勿重复配置");
        // 清空自定义数据
        if (
            DataPermissionConstants.Type.DEPARTMENT_CUSTOM.getCode() != systemDataPermission.getType() &&
            DataPermissionConstants.Type.POSITION_CUSTOM.getCode() != systemDataPermission.getType()
        ) {
            systemDataPermission.setCustomData("");
        }
        return systemDataPermissionService.create(systemDataPermission);
    }

    @Override
    public void update(SystemDataPermission systemDataPermission) {
        // 清空自定义数据
        if (
            DataPermissionConstants.Type.DEPARTMENT_CUSTOM.getCode() != systemDataPermission.getType() &&
            DataPermissionConstants.Type.POSITION_CUSTOM.getCode() != systemDataPermission.getType()
        ) {
            systemDataPermission.setCustomData("");
        }
        systemDataPermissionService.updateById(systemDataPermission);
    }

    @Override
    public void updateStatus(SystemDataPermission systemDataPermission) {
        if (!systemDataPermission.getDisabled()) {
            SystemDataPermission dbPermission = systemDataPermissionService.findById(systemDataPermission.getId());
            if (dbPermission == null) {
                throw new BusinessException(ResponseStatus.DATA_EMPTY.getCode(), ResponseStatus.DATA_EMPTY.getMessage());
            }
            // 验证是否已存在配置
            this.check(dbPermission, "该业务模块与角色存在有效的数据权限，请先禁用后再尝试启动此数据权限");
        }
        systemDataPermissionService.updateById(systemDataPermission);
    }

    private void check (SystemDataPermission systemDataPermission, String message) {
        // 验证是否已存在配置
        SystemDataPermission queryDto = new SystemDataPermission();
        queryDto.setRoleId(systemDataPermission.getRoleId());
        queryDto.setBusinessCode(systemDataPermission.getBusinessCode());
        queryDto.setDisabled(Boolean.FALSE);
        queryDto.setDeleted(Boolean.FALSE);
        if (systemDataPermissionService.count(queryDto) > 0) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), message);
        }
    }
}
