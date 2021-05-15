package com.yiwa.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.SystemRolePermissionMapper;
import com.yiwa.dao.system.model.SystemRolePermission;
import com.yiwa.service.system.SystemRolePermissionService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 角色权限关联Service实现
 * @author Caesar Liu
 * @date 2021/05/15 19:41
 */
@Service
public class SystemRolePermissionServiceImpl implements SystemRolePermissionService {

    @Autowired
    private SystemRolePermissionMapper systemRolePermissionMapper;

    @Override
    public Integer create(SystemRolePermission systemRolePermission) {
        systemRolePermissionMapper.insert(systemRolePermission);
        return systemRolePermission.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemRolePermissionMapper.deleteById(id);
    }

    @Override
    public void delete(SystemRolePermission systemRolePermission) {
        SystemRolePermission newPermission = new SystemRolePermission();
        newPermission.setDeleted(Boolean.TRUE);
        UpdateWrapper<SystemRolePermission> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setEntity(systemRolePermission);
        systemRolePermissionMapper.update(newPermission, updateWrapper);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        systemRolePermissionMapper.deleteBatchIds(ids);
    }

    @Override
    public void updateById(SystemRolePermission systemRolePermission) {
        systemRolePermissionMapper.updateById(systemRolePermission);
    }

    @Override
    public void updateByIdInBatch(List<SystemRolePermission> systemRolePermissions) {
        if (CollectionUtils.isEmpty(systemRolePermissions)) return;
        for (SystemRolePermission systemRolePermission: systemRolePermissions) {
            this.updateById(systemRolePermission);
        }
    }

    @Override
    public SystemRolePermission findById(Integer id) {
        return systemRolePermissionMapper.selectById(id);
    }

    @Override
    public SystemRolePermission findOne(SystemRolePermission systemRolePermission) {
        Wrapper<SystemRolePermission> wrapper = new QueryWrapper<>(systemRolePermission);
        return systemRolePermissionMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemRolePermission> findList(SystemRolePermission systemRolePermission) {
        Wrapper<SystemRolePermission> wrapper = new QueryWrapper<>(systemRolePermission);
        return systemRolePermissionMapper.selectList(wrapper);
    }
  
    @Override
    public PageData<SystemRolePermission> findPage(PageWrap<SystemRolePermission> pageWrap) {
        IPage<SystemRolePermission> page = new Page<>(pageWrap.getPage(), pageWrap.getCapacity());
        QueryWrapper<SystemRolePermission> queryWrapper = new QueryWrapper<>(pageWrap.getModel());
        for(PageWrap.SortData sortData: pageWrap.getSorts()) {
            if (sortData.getDirection().equalsIgnoreCase("DESC")) {
                queryWrapper.orderByDesc(sortData.getProperty());
            } else {
                queryWrapper.orderByAsc(sortData.getProperty());
            }
        }
        return PageData.from(systemRolePermissionMapper.selectPage(page, queryWrapper));
    }

    @Override
    public long count(SystemRolePermission systemRolePermission) {
        Wrapper<SystemRolePermission> wrapper = new QueryWrapper<>(systemRolePermission);
        return systemRolePermissionMapper.selectCount(wrapper);
    }
}