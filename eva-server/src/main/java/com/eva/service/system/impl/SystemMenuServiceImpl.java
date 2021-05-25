package com.eva.service.system.impl;

import com.eva.dao.system.SystemMenuMapper;
import com.eva.dao.system.model.SystemDepartment;
import com.eva.dao.system.model.SystemMenu;
import com.eva.dao.system.vo.SystemMenuListVO;
import com.eva.service.system.SystemMenuService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统菜单Service实现
 * @author Eva
 * @date 2021/05/15 19:41
 */
@Service
public class SystemMenuServiceImpl implements SystemMenuService {

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Override
    public Integer create(SystemMenu systemMenu) {
        systemMenuMapper.insert(systemMenu);
        return systemMenu.getId();
    }

    @Override
    public void deleteById(Integer id) {
        SystemMenu systemMenu = new SystemMenu();
        systemMenu.setId(id);
        systemMenu.setDeleted(Boolean.TRUE);
        this.updateById(systemMenu);
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
    public void updateById(SystemMenu systemMenu) {
        systemMenuMapper.updateById(systemMenu);
    }

    @Override
    public void updateByIdInBatch(List<SystemMenu> systemMenus) {
        if (CollectionUtils.isEmpty(systemMenus)) return;
        for (SystemMenu systemMenu: systemMenus) {
            this.updateById(systemMenu);
        }
    }

    @Override
    public SystemMenu findById(Integer id) {
        return systemMenuMapper.selectById(id);
    }

    @Override
    public SystemMenu findOne(SystemMenu systemMenu) {
        Wrapper<SystemMenu> wrapper = new QueryWrapper<>(systemMenu);
        return systemMenuMapper.selectOne(wrapper);
    }

    @Override
    public List<SystemMenu> findList(SystemMenu systemMenu) {
        QueryWrapper<SystemMenu> wrapper = new QueryWrapper<>(systemMenu);
        wrapper.lambda().orderByAsc(SystemMenu::getSort);
        return systemMenuMapper.selectList(wrapper);
    }

    @Override
    public List<SystemMenuListVO> findList() {
        return systemMenuMapper.selectManageList();
    }

    @Override
    public List<SystemMenu> findRootList() {
        QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .isNull(SystemMenu::getParentId)
                .eq(SystemMenu::getDeleted, Boolean.FALSE)
                .orderByAsc(SystemMenu::getSort);
        return systemMenuMapper.selectList(queryWrapper);
    }

    @Override
    public List<SystemMenu> findByUserId(Integer userId) {
        return systemMenuMapper.selectByUserId(userId);
    }

    @Override
    public List<SystemMenu> findByRoleId(Integer roleId) {
        return systemMenuMapper.selectByRoleId(roleId);
    }

    @Override
    public long count(SystemMenu systemMenu) {
        Wrapper<SystemMenu> wrapper = new QueryWrapper<>(systemMenu);
        return systemMenuMapper.selectCount(wrapper);
    }

    @Override
    public List<Integer> findChildren(Integer menuId) {
        List<Integer> pool = new ArrayList<>();
        this.fillChildren(pool, Arrays.asList(menuId));
        return pool;
    }

    /**
     * 获取子部门ID
     * @author Eva
     * @date 2021-05-22 23:22
     */
    private void fillChildren(List<Integer> pool, List<Integer> parentIds) {
        QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SystemMenu::getDeleted, Boolean.FALSE)
                .in(SystemMenu::getParentId, parentIds);
        List<SystemMenu> departments = systemMenuMapper.selectList(queryWrapper);
        List<Integer> ids = departments.stream().map(SystemMenu::getId).collect(Collectors.toList());
        if (ids.size() > 0) {
            pool.addAll(ids);
            this.fillChildren(pool, ids);
        }
    }
}