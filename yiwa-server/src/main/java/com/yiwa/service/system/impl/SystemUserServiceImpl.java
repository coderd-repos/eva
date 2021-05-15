package com.yiwa.service.system.impl;

import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.core.utils.WrapperUtil;
import com.yiwa.dao.system.SystemUserMapper;
import com.yiwa.dao.system.model.SystemUser;
import com.yiwa.service.system.SystemUserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public PageData<SystemUser> findPage(PageWrap<SystemUser> pageWrap) {
        IPage<SystemUser> page = new Page<>(pageWrap.getPage(), pageWrap.getCapacity());
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>(WrapperUtil.blankToNull(pageWrap.getModel()));
        for(PageWrap.SortData sortData: pageWrap.getSorts()) {
            if (sortData.getDirection().equalsIgnoreCase("DESC")) {
                queryWrapper.orderByDesc(sortData.getProperty());
            } else {
                queryWrapper.orderByAsc(sortData.getProperty());
            }
        }
        return PageData.from(systemUserMapper.selectPage(page, queryWrapper));
    }

    @Override
    public long count(SystemUser systemUser) {
        Wrapper<SystemUser> wrapper = new QueryWrapper<>(systemUser);
        return systemUserMapper.selectCount(wrapper);
    }
}