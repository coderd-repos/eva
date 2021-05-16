package com.yiwa.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.core.utils.WrapperUtil;
import com.yiwa.dao.system.SystemRoleMapper;
import com.yiwa.dao.system.dto.QuerySystemRoleDTO;
import com.yiwa.dao.system.model.SystemRole;
import com.yiwa.dao.system.vo.SystemRoleListVO;
import com.yiwa.service.system.SystemMenuService;
import com.yiwa.service.system.SystemPermissionService;
import com.yiwa.service.system.SystemRoleService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private SystemMenuService systemMenuService;

    @Autowired
    private SystemPermissionService systemPermissionService;

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
    public PageData<SystemRoleListVO> findPage(PageWrap<QuerySystemRoleDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<SystemRoleListVO> roleList = systemRoleMapper.selectList(pageWrap.getModel());
        for (SystemRoleListVO role : roleList) {
            role.setMenus(systemMenuService.findByRoleId(role.getId()));
            role.setPermissions(systemPermissionService.findByRoleId(role.getId()));
        }
        return PageData.from(new PageInfo<>(roleList));
    }

    @Override
    public long count(SystemRole systemRole) {
        Wrapper<SystemRole> wrapper = new QueryWrapper<>(systemRole);
        return systemRoleMapper.selectCount(wrapper);
    }
}