package com.yiwa.service.system.impl;

import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.SystemRoleMapper;
import com.yiwa.dao.system.model.SystemRole;
import com.yiwa.service.system.SystemRoleService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 系统角色Service实现
 * @author Caesar Liu
 * @date 2021/05/15 19:41
 */
@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    @Autowired
    private SystemRoleMapper systemRoleMapper;

    @Override
    public Integer create(SystemRole systemRole) {
        systemRoleMapper.insert(systemRole);
        return systemRole.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemRoleMapper.deleteById(id);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        systemRoleMapper.deleteBatchIds(ids);
    }

    @Override
    public void updateById(SystemRole systemRole) {
        systemRoleMapper.updateById(systemRole);
    }

    @Override
    public void updateByIdInBatch(List<SystemRole> systemRoles) {
        if (CollectionUtils.isEmpty(systemRoles)) return;
        for (SystemRole systemRole: systemRoles) {
            this.updateById(systemRole);
        }
    }

    @Override
    public SystemRole findById(Integer id) {
        return systemRoleMapper.selectById(id);
    }

    @Override
    public List<SystemRole> findByUserId(Integer userId) {
        return systemRoleMapper.selectByUserId(userId);
    }

    @Override
    public SystemRole findOne(SystemRole systemRole) {
        Wrapper<SystemRole> wrapper = new QueryWrapper<>(systemRole);
        return systemRoleMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemRole> findList(SystemRole systemRole) {
        Wrapper<SystemRole> wrapper = new QueryWrapper<>(systemRole);
        return systemRoleMapper.selectList(wrapper);
    }
  
    @Override
    public PageData<SystemRole> findPage(PageWrap<SystemRole> pageWrap) {
        IPage<SystemRole> page = new Page<>(pageWrap.getPage(), pageWrap.getCapacity());
        QueryWrapper<SystemRole> queryWrapper = new QueryWrapper<>(pageWrap.getModel());
        for(PageWrap.SortData sortData: pageWrap.getSorts()) {
            if (sortData.getDirection().equalsIgnoreCase("DESC")) {
                queryWrapper.orderByDesc(sortData.getProperty());
            } else {
                queryWrapper.orderByAsc(sortData.getProperty());
            }
        }
        return PageData.from(systemRoleMapper.selectPage(page, queryWrapper));
    }

    @Override
    public long count(SystemRole systemRole) {
        Wrapper<SystemRole> wrapper = new QueryWrapper<>(systemRole);
        return systemRoleMapper.selectCount(wrapper);
    }
}