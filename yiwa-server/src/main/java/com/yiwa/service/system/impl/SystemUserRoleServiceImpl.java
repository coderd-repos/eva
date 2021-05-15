package com.yiwa.service.system.impl;

import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.SystemUserRoleMapper;
import com.yiwa.dao.system.model.SystemUserRole;
import com.yiwa.service.system.SystemUserRoleService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户角色关联Service实现
 * @author Caesar Liu
 * @date 2021/05/15 19:41
 */
@Service
public class SystemUserRoleServiceImpl implements SystemUserRoleService {

    @Autowired
    private SystemUserRoleMapper systemUserRoleMapper;

    @Override
    public Integer create(SystemUserRole systemUserRole) {
        systemUserRoleMapper.insert(systemUserRole);
        return systemUserRole.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemUserRoleMapper.deleteById(id);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        systemUserRoleMapper.deleteBatchIds(ids);
    }

    @Override
    public void updateById(SystemUserRole systemUserRole) {
        systemUserRoleMapper.updateById(systemUserRole);
    }

    @Override
    public void updateByIdInBatch(List<SystemUserRole> systemUserRoles) {
        if (CollectionUtils.isEmpty(systemUserRoles)) return;
        for (SystemUserRole systemUserRole: systemUserRoles) {
            this.updateById(systemUserRole);
        }
    }

    @Override
    public SystemUserRole findById(Integer id) {
        return systemUserRoleMapper.selectById(id);
    }

    @Override
    public SystemUserRole findOne(SystemUserRole systemUserRole) {
        Wrapper<SystemUserRole> wrapper = new QueryWrapper<>(systemUserRole);
        return systemUserRoleMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemUserRole> findList(SystemUserRole systemUserRole) {
        Wrapper<SystemUserRole> wrapper = new QueryWrapper<>(systemUserRole);
        return systemUserRoleMapper.selectList(wrapper);
    }
  
    @Override
    public PageData<SystemUserRole> findPage(PageWrap<SystemUserRole> pageWrap) {
        IPage<SystemUserRole> page = new Page<>(pageWrap.getPage(), pageWrap.getCapacity());
        QueryWrapper<SystemUserRole> queryWrapper = new QueryWrapper<>(pageWrap.getModel());
        for(PageWrap.SortData sortData: pageWrap.getSorts()) {
            if (sortData.getDirection().equalsIgnoreCase("DESC")) {
                queryWrapper.orderByDesc(sortData.getProperty());
            } else {
                queryWrapper.orderByAsc(sortData.getProperty());
            }
        }
        return PageData.from(systemUserRoleMapper.selectPage(page, queryWrapper));
    }

    @Override
    public long count(SystemUserRole systemUserRole) {
        Wrapper<SystemUserRole> wrapper = new QueryWrapper<>(systemUserRole);
        return systemUserRoleMapper.selectCount(wrapper);
    }
}