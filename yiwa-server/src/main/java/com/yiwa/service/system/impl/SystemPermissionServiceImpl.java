package com.yiwa.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.core.utils.WrapperUtil;
import com.yiwa.dao.system.SystemPermissionMapper;
import com.yiwa.dao.system.dto.QuerySystemPermissionDTO;
import com.yiwa.dao.system.model.SystemPermission;
import com.yiwa.dao.system.vo.SystemPermissionListVO;
import com.yiwa.service.system.SystemPermissionService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 系统权限Service实现
 * @author Yiwa
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
        SystemPermission systemPermission = new SystemPermission();
        systemPermission.setId(id);
        systemPermission.setDeleted(Boolean.TRUE);
        this.updateById(systemPermission);
    }

    @Override
    @Transactional
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        for (Integer id : ids) {
            this.deleteById(id);
        }
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
    public List<SystemPermission> findByRoleId(Integer roleId) {
        return systemPermissionMapper.selectByRoleId(roleId);
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
    public PageData<SystemPermissionListVO> findPage(PageWrap<QuerySystemPermissionDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        return PageData.from(new PageInfo<>(systemPermissionMapper.selectManageList(pageWrap.getModel())));
    }

    @Override
    public long count(SystemPermission systemPermission) {
        Wrapper<SystemPermission> wrapper = new QueryWrapper<>(systemPermission);
        return systemPermissionMapper.selectCount(wrapper);
    }
}