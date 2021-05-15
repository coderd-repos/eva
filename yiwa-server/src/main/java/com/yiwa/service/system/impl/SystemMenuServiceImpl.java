package com.yiwa.service.system.impl;

import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.core.utils.WrapperUtil;
import com.yiwa.dao.system.SystemMenuMapper;
import com.yiwa.dao.system.model.SystemMenu;
import com.yiwa.dao.system.vo.SystemMenuListVO;
import com.yiwa.service.system.SystemMenuService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 系统菜单Service实现
 * @author Caesar Liu
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
        systemMenuMapper.deleteById(id);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        systemMenuMapper.deleteBatchIds(ids);
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
        Wrapper<SystemMenu> wrapper = new QueryWrapper<>(systemMenu);
        return systemMenuMapper.selectList(wrapper);
    }

    @Override
    public List<SystemMenuListVO> findList() {
        return systemMenuMapper.selectList();
    }

    @Override
    public List<SystemMenu> findRootList() {
        QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("SORT");
        queryWrapper.lambda().isNull(SystemMenu::getParentId);
        queryWrapper.lambda().eq(SystemMenu::getDeleted, Boolean.FALSE);
        return systemMenuMapper.selectList(queryWrapper);
    }

    @Override
    public List<SystemMenu> findByUserId(Integer userId) {
        return systemMenuMapper.selectByUserId(userId);
    }

    @Override
    public PageData<SystemMenu> findPage(PageWrap<SystemMenu> pageWrap) {
        IPage<SystemMenu> page = new Page<>(pageWrap.getPage(), pageWrap.getCapacity());
        QueryWrapper<SystemMenu> queryWrapper = new QueryWrapper<>(WrapperUtil.blankToNull(pageWrap.getModel()));
        for(PageWrap.SortData sortData: pageWrap.getSorts()) {
            if (sortData.getDirection().equalsIgnoreCase("DESC")) {
                queryWrapper.orderByDesc(sortData.getProperty());
            } else {
                queryWrapper.orderByAsc(sortData.getProperty());
            }
        }
        return PageData.from(systemMenuMapper.selectPage(page, queryWrapper));
    }

    @Override
    public long count(SystemMenu systemMenu) {
        Wrapper<SystemMenu> wrapper = new QueryWrapper<>(systemMenu);
        return systemMenuMapper.selectCount(wrapper);
    }
}