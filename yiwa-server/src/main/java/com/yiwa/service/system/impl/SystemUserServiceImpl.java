package com.yiwa.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.SystemUserMapper;
import com.yiwa.dao.system.dto.QuerySystemUserDTO;
import com.yiwa.dao.system.model.SystemUser;
import com.yiwa.dao.system.vo.SystemUserListVO;
import com.yiwa.service.system.SystemRoleService;
import com.yiwa.service.system.SystemUserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 系统用户Service实现
 * @author Caesar Liu
 * @date 2021/05/15 19:41
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private SystemRoleService systemRoleService;

    @Override
    public Integer create(SystemUser systemUser) {
        systemUserMapper.insert(systemUser);
        return systemUser.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemUserMapper.deleteById(id);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        systemUserMapper.deleteBatchIds(ids);
    }

    @Override
    public void updateById(SystemUser systemUser) {
        systemUserMapper.updateById(systemUser);
    }

    @Override
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
        List<SystemUserListVO> userList = systemUserMapper.selectList(pageWrap.getModel());
        for (SystemUserListVO user : userList) {
            user.setRoles(systemRoleService.findByUserId(user.getId()));
        }
        return PageData.from(new PageInfo<>(userList));
    }

    @Override
    public long count(SystemUser systemUser) {
        Wrapper<SystemUser> wrapper = new QueryWrapper<>(systemUser);
        return systemUserMapper.selectCount(wrapper);
    }
}