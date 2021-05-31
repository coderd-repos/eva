package com.eva.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.core.utils.Utils;
import com.eva.dao.system.SystemRoleMenuMapper;
import com.eva.dao.system.model.SystemRoleMenu;
import com.eva.service.system.SystemRoleMenuService;
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
 * 角色菜单关联Service实现
 * @author Eva.Caesar Liu
 * @date 2021/05/15 19:41
 */
@Service
public class SystemRoleMenuServiceImpl implements SystemRoleMenuService {

    @Autowired
    private SystemRoleMenuMapper systemRoleMenuMapper;

    @Override
    public Integer create(SystemRoleMenu systemRoleMenu) {
        systemRoleMenuMapper.insert(systemRoleMenu);
        return systemRoleMenu.getId();
    }

    @Override
    public void deleteById(Integer id) {
        SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
        systemRoleMenu.setId(id);
        systemRoleMenu.setDeleted(Boolean.TRUE);
        this.updateById(systemRoleMenu);
    }

    @Override
    public void delete(SystemRoleMenu systemRoleMenu) {
        SystemRoleMenu newRoleMenu = new SystemRoleMenu();
        newRoleMenu.setDeleted(Boolean.TRUE);
        UpdateWrapper<SystemRoleMenu> updateWrapper = new UpdateWrapper<>(systemRoleMenu);
        systemRoleMenuMapper.update(newRoleMenu, updateWrapper);
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
    public void updateById(SystemRoleMenu systemRoleMenu) {
        systemRoleMenuMapper.updateById(systemRoleMenu);
    }

    @Override
    @Transactional
    public void updateByIdInBatch(List<SystemRoleMenu> systemRoleMenus) {
        if (CollectionUtils.isEmpty(systemRoleMenus)) return;
        for (SystemRoleMenu systemRoleMenu: systemRoleMenus) {
            this.updateById(systemRoleMenu);
        }
    }

    @Override
    public SystemRoleMenu findById(Integer id) {
        return systemRoleMenuMapper.selectById(id);
    }

    @Override
    public SystemRoleMenu findOne(SystemRoleMenu systemRoleMenu) {
        Wrapper<SystemRoleMenu> wrapper = new QueryWrapper<>(systemRoleMenu);
        return systemRoleMenuMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemRoleMenu> findList(SystemRoleMenu systemRoleMenu) {
        Wrapper<SystemRoleMenu> wrapper = new QueryWrapper<>(systemRoleMenu);
        return systemRoleMenuMapper.selectList(wrapper);
    }
  
    @Override
    public PageData<SystemRoleMenu> findPage(PageWrap<SystemRoleMenu> pageWrap) {
        IPage<SystemRoleMenu> page = new Page<>(pageWrap.getPage(), pageWrap.getCapacity());
        QueryWrapper<SystemRoleMenu> queryWrapper = new QueryWrapper<>(Utils.MP.blankToNull(pageWrap.getModel()));
        for(PageWrap.SortData sortData: pageWrap.getSorts()) {
            if (sortData.getDirection().equalsIgnoreCase("DESC")) {
                queryWrapper.orderByDesc(sortData.getProperty());
            } else {
                queryWrapper.orderByAsc(sortData.getProperty());
            }
        }
        return PageData.from(systemRoleMenuMapper.selectPage(page, queryWrapper));
    }

    @Override
    public long count(SystemRoleMenu systemRoleMenu) {
        Wrapper<SystemRoleMenu> wrapper = new QueryWrapper<>(systemRoleMenu);
        return systemRoleMenuMapper.selectCount(wrapper);
    }
}