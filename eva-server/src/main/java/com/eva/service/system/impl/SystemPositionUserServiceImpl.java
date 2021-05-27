package com.eva.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.eva.dao.system.SystemPositionUserMapper;
import com.eva.dao.system.model.SystemPositionUser;
import com.eva.service.system.SystemPositionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 岗位用户Service实现
 * @author Eva
 * @date 2021/05/22 09:35
 */
@Service
public class SystemPositionUserServiceImpl implements SystemPositionUserService {

    @Autowired
    private SystemPositionUserMapper systemPositionUserMapper;

    @Override
    public Integer create(SystemPositionUser systemPositionUser) {
        systemPositionUserMapper.insert(systemPositionUser);
        return systemPositionUser.getId();
    }

    @Override
    public void deleteById(Integer id) {
        SystemPositionUser systemPositionUser = new SystemPositionUser();
        systemPositionUser.setId(id);
        systemPositionUser.setDeleted(Boolean.FALSE);
        this.updateById(systemPositionUser);
    }

    @Override
    public void delete(SystemPositionUser dto) {
        SystemPositionUser newSystemPositionUser = new SystemPositionUser();
        newSystemPositionUser.setDeleted(Boolean.TRUE);
        UpdateWrapper<SystemPositionUser> updateWrapper = new UpdateWrapper<>(dto);
        systemPositionUserMapper.update(newSystemPositionUser, updateWrapper);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        for (Integer id: ids) {
            this.deleteById(id);
        }
    }

    @Override
    public void updateById(SystemPositionUser systemPositionUser) {
        systemPositionUserMapper.updateById(systemPositionUser);
    }

    @Override
    @Transactional
    public void updateByIdInBatch(List<SystemPositionUser> systemPositionUsers) {
        if (CollectionUtils.isEmpty(systemPositionUsers)) return;
        for (SystemPositionUser systemPositionUser: systemPositionUsers) {
            this.updateById(systemPositionUser);
        }
    }

    @Override
    public SystemPositionUser findById(Integer id) {
        return systemPositionUserMapper.selectById(id);
    }

    @Override
    public SystemPositionUser findOne(SystemPositionUser systemPositionUser) {
        Wrapper<SystemPositionUser> wrapper = new QueryWrapper<>(systemPositionUser);
        return systemPositionUserMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemPositionUser> findList(SystemPositionUser systemPositionUser) {
        Wrapper<SystemPositionUser> wrapper = new QueryWrapper<>(systemPositionUser);
        return systemPositionUserMapper.selectList(wrapper);
    }

    @Override
    public long count(SystemPositionUser systemPositionUser) {
        Wrapper<SystemPositionUser> wrapper = new QueryWrapper<>(systemPositionUser);
        return systemPositionUserMapper.selectCount(wrapper);
    }
}