package com.eva.biz.system.impl;

import com.eva.biz.system.SystemPermissionBiz;
import com.eva.core.exception.BusinessException;
import com.eva.core.model.ResponseStatus;
import com.eva.dao.system.model.SystemPermission;
import com.eva.dao.system.model.SystemRole;
import com.eva.service.system.SystemPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SystemPermissionBizImpl implements SystemPermissionBiz {

    @Autowired
    private SystemPermissionService systemPermissionService;

    @Override
    public void deleteById(Integer id) {
        SystemPermission permission = systemPermissionService.findById(id);
        if (permission == null) {
            return;
        }
        if (permission.getFixed()) {
            throw new BusinessException(ResponseStatus.NOT_ALLOWED.getCode(), "请勿删除" + permission.getName() + "，因为这是固定权限");
        }
        systemPermissionService.deleteById(id);
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
    public Integer create(SystemPermission systemPermission) {
        SystemPermission queryDto = new SystemPermission();
        queryDto.setCode(systemPermission.getCode());
        queryDto.setDeleted(Boolean.FALSE);
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
        queryDto.setDeleted(Boolean.FALSE);
        SystemPermission permission = systemPermissionService.findOne(queryDto);
        if (permission != null && !systemPermission.getId().equals(permission.getId())) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "权限编码已存在");
        }
        systemPermissionService.updateById(systemPermission);
    }
}
