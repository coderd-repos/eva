package com.eva.service.system.impl;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.SystemDataPermissionMapper;
import com.eva.dao.system.SystemRoleMapper;
import com.eva.dao.system.model.SystemDataPermission;
import com.eva.dao.system.model.SystemRole;
import com.eva.dao.system.vo.SystemDataPermissionListVO;
import com.eva.service.system.SystemDataPermissionService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 数据权限配置Service实现
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Service
public class SystemDataPermissionServiceImpl implements SystemDataPermissionService {

    @Autowired
    private SystemDataPermissionMapper systemDataPermissionMapper;

    @Autowired
    private SystemRoleMapper systemRoleMapper;

    @Override
    public Integer create(SystemDataPermission systemDataPermission) {
        systemDataPermissionMapper.insert(systemDataPermission);
        return systemDataPermission.getId();
    }

    @Override
    public void deleteById(Integer id) {
        SystemDataPermission dataPermission = new SystemDataPermission();
        dataPermission.setId(id);
        dataPermission.setDeleted(Boolean.TRUE);
        this.updateById(dataPermission);
    }

    @Override
    public void delete(SystemDataPermission systemDataPermission) {
        UpdateWrapper<SystemDataPermission> deleteWrapper = new UpdateWrapper<>(systemDataPermission);
        systemDataPermissionMapper.delete(deleteWrapper);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        systemDataPermissionMapper.deleteBatchIds(ids);
    }

    @Override
    public void updateById(SystemDataPermission systemDataPermission) {
        systemDataPermissionMapper.updateById(systemDataPermission);
    }

    @Override
    public void updateByIdInBatch(List<SystemDataPermission> systemDataPermissions) {
        if (CollectionUtils.isEmpty(systemDataPermissions)) {
            return;
        }
        for (SystemDataPermission systemDataPermission: systemDataPermissions) {
            this.updateById(systemDataPermission);
        }
    }

    @Override
    public SystemDataPermission findById(Integer id) {
        return systemDataPermissionMapper.selectById(id);
    }

    @Override
    public SystemDataPermission findOne(SystemDataPermission systemDataPermission) {
        Wrapper<SystemDataPermission> wrapper = new QueryWrapper<>(systemDataPermission);
        return systemDataPermissionMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemDataPermission> findList(SystemDataPermission systemDataPermission) {
        Wrapper<SystemDataPermission> wrapper = new QueryWrapper<>(systemDataPermission);
        return systemDataPermissionMapper.selectList(wrapper);
    }
  
    @Override
    public PageData<SystemDataPermissionListVO> findPage(PageWrap<SystemDataPermission> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        return PageData.from(new PageInfo<>(systemDataPermissionMapper.selectManageList(pageWrap.getModel(), pageWrap.getOrderByClause())));
    }

    @Override
    public List<SystemDataPermission> findDataPermission(String businessCode, List<String> roles) {
        if (CollectionUtils.isEmpty(roles)) {
            return Collections.emptyList();
        }
        // 查询角色
        QueryWrapper<SystemRole> queryRoleWrapper = new QueryWrapper<>();
        queryRoleWrapper.lambda().in(SystemRole::getCode, roles).eq(SystemRole::getDeleted, Boolean.FALSE);
        List<SystemRole> systemRoles = systemRoleMapper.selectList(queryRoleWrapper);
        if (CollectionUtils.isEmpty(systemRoles)) {
            return Collections.emptyList();
        }
        List<Integer> roleIds = new ArrayList<>();
        for (SystemRole role : systemRoles) {
            roleIds.add(role.getId());
        }
        // 查询数据权限
        QueryWrapper<SystemDataPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SystemDataPermission::getBusinessCode, businessCode)
                .in(SystemDataPermission::getRoleId, roleIds)
                .eq(SystemDataPermission::getDeleted, Boolean.FALSE)
                .eq(SystemDataPermission::getDisabled, Boolean.FALSE);
        return systemDataPermissionMapper.selectList(queryWrapper);
    }

    @Override
    public long count(SystemDataPermission systemDataPermission) {
        Wrapper<SystemDataPermission> wrapper = new QueryWrapper<>(systemDataPermission);
        return systemDataPermissionMapper.selectCount(wrapper);
    }
}
