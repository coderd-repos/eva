package com.yiwa.service.system.impl;

import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.SystemPermissionMapper;
import com.yiwa.dao.system.model.SystemPermission;
import com.yiwa.service.system.SystemPermissionService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 系统权限Service实现
 * @author Caesar Liu
 * @date 2021/05/15 19:41
 */
@Service
public class SystemPermissionServiceImpl implements SystemPermissionService {

    @Autowired
    private SystemPermissionMapper systemPermissionMapper;

    @Override
    public Integer create(SystemPermission systemPermission) {
        systemPermissionMapper.insert(systemPermission);
        return systemPermission.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemPermissionMapper.deleteById(id);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        systemPermissionMapper.deleteBatchIds(ids);
    }

    @Override
    public void updateById(SystemPermission systemPermission) {
        systemPermissionMapper.updateById(systemPermission);
    }

    @Override
    public void updateByIdInBatch(List<SystemPermission> systemPermissions) {
        if (CollectionUtils.isEmpty(systemPermissions)) return;
        for (SystemPermission systemPermission: systemPermissions) {
            this.updateById(systemPermission);
        }
    }

    @Override
    public SystemPermission findById(Integer id) {
        return systemPermissionMapper.selectById(id);
    }

    @Override
    public List<SystemPermission> findByUserId(Integer userId) {
        return systemPermissionMapper.selectByUserId(userId);
    }

    @Override
    public SystemPermission findOne(SystemPermission systemPermission) {
        Wrapper<SystemPermission> wrapper = new QueryWrapper<>(systemPermission);
        return systemPermissionMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemPermission> findList(SystemPermission systemPermission) {
        Wrapper<SystemPermission> wrapper = new QueryWrapper<>(systemPermission);
        return systemPermissionMapper.selectList(wrapper);
    }
  
    @Override
    public PageData<SystemPermission> findPage(PageWrap<SystemPermission> pageWrap) {
        IPage<SystemPermission> page = new Page<>(pageWrap.getPage(), pageWrap.getCapacity());
        QueryWrapper<SystemPermission> queryWrapper = new QueryWrapper<>(pageWrap.getModel());
        for(PageWrap.SortData sortData: pageWrap.getSorts()) {
            if (sortData.getDirection().equalsIgnoreCase("DESC")) {
                queryWrapper.orderByDesc(sortData.getProperty());
            } else {
                queryWrapper.orderByAsc(sortData.getProperty());
            }
        }
        return PageData.from(systemPermissionMapper.selectPage(page, queryWrapper));
    }

    @Override
    public long count(SystemPermission systemPermission) {
        Wrapper<SystemPermission> wrapper = new QueryWrapper<>(systemPermission);
        return systemPermissionMapper.selectCount(wrapper);
    }
}