package com.eva.service.system.impl;

import com.eva.service.system.SystemPositionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.SystemUserMapper;
import com.eva.dao.system.dto.QuerySystemUserDTO;
import com.eva.dao.system.model.SystemUser;
import com.eva.dao.system.vo.SystemUserListVO;
import com.eva.service.system.SystemDepartmentService;
import com.eva.service.system.SystemRoleService;
import com.eva.service.system.SystemUserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 系统用户Service实现
 * @author Eva.Caesar Liu
 * @date 2021/05/15 19:41
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private SystemRoleService systemRoleService;

    @Autowired
    private SystemDepartmentService systemDepartmentService;

    @Autowired
    private SystemPositionService systemPositionService;

    @Override
    public Integer create(SystemUser systemUser) {
        systemUserMapper.insert(systemUser);
        return systemUser.getId();
    }

    @Override
    public void deleteById(Integer id) {
        SystemUser systemUser = new SystemUser();
        systemUser.setId(id);
        systemUser.setDeleted(Boolean.TRUE);
        this.updateById(systemUser);
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
    public void updateById(SystemUser systemUser) {
        systemUserMapper.updateById(systemUser);
    }

    @Override
    @Transactional
    public void updateByIdInBatch(List<SystemUser> systemUsers) {
        if (CollectionUtils.isEmpty(systemUsers)) return;
        for (SystemUser systemUser: systemUsers) {
            this.updateById(systemUser);
        }
    }

    @Override
    public SystemUser findById(Integer id) {
        return systemUserMapper.selectById(id);
    }

    @Override
    public SystemUser findOne(SystemUser systemUser) {
        Wrapper<SystemUser> wrapper = new QueryWrapper<>(systemUser);
        return systemUserMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemUser> findList(SystemUser systemUser) {
        Wrapper<SystemUser> wrapper = new QueryWrapper<>(systemUser);
        return systemUserMapper.selectList(wrapper);
    }
  
    @Override
    public PageData<SystemUserListVO> findPage(PageWrap<QuerySystemUserDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        // 根部门条件处理（需查询根部门下所有部门的用户）
        if (pageWrap.getModel().getRootDeptId() != null) {
            List<Integer> departmentIds = systemDepartmentService.findChildren(pageWrap.getModel().getRootDeptId());
            departmentIds.add(pageWrap.getModel().getRootDeptId());
            pageWrap.getModel().setDepartmentIds(departmentIds);
        }
        // 执行查询
        List<SystemUserListVO> userList = systemUserMapper.selectManageList(pageWrap.getModel(), pageWrap.getOrderByClause());
        for (SystemUserListVO user : userList) {
            // 查询用户角色列表
            user.setRoles(systemRoleService.findByUserId(user.getId()));
            // 查询用户岗位列表
            user.setPositions(systemPositionService.findByUserId(user.getId()));
        }
        return PageData.from(new PageInfo<>(userList));
    }

    @Override
    public long count(SystemUser systemUser) {
        Wrapper<SystemUser> wrapper = new QueryWrapper<>(systemUser);
        return systemUserMapper.selectCount(wrapper);
    }
}