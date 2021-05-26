package com.eva.biz.system.impl;

import com.eva.biz.system.SystemMenuBiz;
import com.eva.core.model.BusinessException;
import com.eva.core.model.ResponseStatus;
import com.eva.dao.system.dto.UpdateSystemMenuSortDTO;
import com.eva.dao.system.model.SystemMenu;
import com.eva.dao.system.model.SystemPermission;
import com.eva.dao.system.vo.SystemMenuListVO;
import com.eva.dao.system.vo.SystemMenuNodeVO;
import com.eva.service.system.SystemMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemMenuBizImpl implements SystemMenuBiz {

    @Autowired
    private SystemMenuService systemMenuService;

    @Override
    public Integer create(SystemMenu systemMenu) {
        // 统计上级菜单下子菜单数量
        SystemMenu countDto = new SystemMenu();
        countDto.setParentId(systemMenu.getParentId());
        countDto.setDeleted(Boolean.FALSE);
        long subMenuCount = systemMenuService.count(countDto);
        // 设置新建部门的顺序
        systemMenu.setSort(Integer.valueOf("" + subMenuCount));
        return systemMenuService.create(systemMenu);
    }

    @Override
    public void updateById(SystemMenu systemMenu) {
        SystemMenu dbMenu = systemMenuService.findById(systemMenu.getId());
        // 如果上级菜单发生了变化，则重新调整菜单的排序
        if (dbMenu.getParentId() != systemMenu.getParentId() && dbMenu.getParentId() != null && !dbMenu.getParentId().equals(systemMenu.getParentId())) {
            // 统计上级菜单下子菜单数量
            SystemMenu countDto = new SystemMenu();
            countDto.setParentId(systemMenu.getParentId());
            countDto.setDeleted(Boolean.FALSE);
            long subMenuCount = systemMenuService.count(countDto);
            systemMenu.setSort(Integer.valueOf("" + subMenuCount));
        }
        systemMenuService.updateById(systemMenu);
    }

    @Override
    public void updateSort(UpdateSystemMenuSortDTO dto) {
        SystemMenu currentMenu = systemMenuService.findById(dto.getId());
        List<SystemMenu> menuPool;
        if (currentMenu.getParentId() == null) {
            menuPool = systemMenuService.findRootList();
        } else {
            SystemMenu queryDto = new SystemMenu();
            queryDto.setParentId(currentMenu.getParentId());
            menuPool = systemMenuService.findList(queryDto);
        }
        int currentMenuIndex = 0;
        for (int i = 0; i < menuPool.size(); i++) {
            if (menuPool.get(i).getId().equals(dto.getId())) {
                currentMenuIndex = i;
                break;
            }
        }
        // 上移
        if ("top".equals(dto.getDirection())) {
            if (currentMenuIndex - 1 < 0) {
                return;
            }
            SystemMenu preMenu = menuPool.remove(currentMenuIndex - 1);
            menuPool.add(currentMenuIndex, preMenu);
        }
        // 下移
        else {
            if (currentMenuIndex + 1 > menuPool.size() - 1) {
                return;
            }
            SystemMenu nextMenu = menuPool.remove(currentMenuIndex + 1);
            menuPool.add(currentMenuIndex, nextMenu);
        }
        for (int i = 0; i < menuPool.size(); i++) {
            menuPool.get(i).setSort(i);
        }
        // 修改
        systemMenuService.updateByIdInBatch(menuPool);
    }

    @Override
    public List<SystemMenuListVO> findTree() {
        List<SystemMenuListVO> menus = systemMenuService.findList();
        List<SystemMenuListVO> rootMenus = new ArrayList<>();
        // 添加根菜单
        for (SystemMenu menu : menus) {
            if (menu.getParentId() == null) {
                SystemMenuListVO rootMenu = new SystemMenuListVO();
                BeanUtils.copyProperties(menu, rootMenu, "children");
                rootMenu.setChildren(new ArrayList<>());
                rootMenus.add(rootMenu);
            }
        }
        menus.removeIf(menu -> menu.getParentId() == null);
        for (SystemMenuListVO child : rootMenus) {
            this.fillChildren(child, menus);
        }
        return rootMenus;
    }

    @Override
    public List<SystemMenuNodeVO> findTree (Integer userId) {
        SystemMenu queryDto = new SystemMenu();
        queryDto.setDeleted(Boolean.FALSE);
        List<SystemMenu> menus = systemMenuService.findByUserId(userId);
        List<SystemMenuNodeVO> rootNodes = new ArrayList<>();
        // 添加根菜单
        for (SystemMenu menu : menus) {
            if (menu.getParentId() == null) {
                SystemMenuNodeVO nodeVO = new SystemMenuNodeVO();
                nodeVO.setId(menu.getId());
                nodeVO.setIndex("menu_" + menu.getId());
                nodeVO.setLabel(menu.getName());
                nodeVO.setUrl(menu.getPath());
                nodeVO.setIcon(menu.getIcon());
                nodeVO.setChildren(new ArrayList<>());
                rootNodes.add(nodeVO);
            }
        }
        menus.removeIf(menu -> menu.getParentId() == null);
        for (SystemMenuNodeVO child : rootNodes) {
            this.fillChildren(child, menus);
        }
        return rootNodes;
    }

    @Override
    public void deleteById(Integer id) {
        List<Integer> ids = systemMenuService.findChildren(id);
        ids.add(id);
        for (Integer id2 : ids) {
            SystemMenu menu = systemMenuService.findById(id2);
            if (menu == null) {
                continue;
            }
            if (menu.getFixed()) {
                throw new BusinessException(ResponseStatus.NOT_ALLOWED.getCode(), "请勿删除" + menu.getName() + ", 因为这是固定菜单");
            }
        }
        systemMenuService.deleteByIdInBatch(ids);
    }

    @Override
    @Transactional
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    /**
     * 填充子菜单
     * @author Eva
     * @date 2021-03-29 16:09
     */
    private void fillChildren(SystemMenuListVO parentMenu, List<SystemMenuListVO> menus) {
        if (menus.size() == 0) {
            return;
        }
        List<Integer> handledIds = new ArrayList<>();
        for (SystemMenu menu : menus) {
            if (menu.getParentId().equals(parentMenu.getId())) {
                SystemMenuListVO child = new SystemMenuListVO();
                BeanUtils.copyProperties(menu, child, "children");
                child.setChildren(new ArrayList<>());
                parentMenu.getChildren().add(child);
                handledIds.add(menu.getId());
            }
        }
        menus.removeIf(menu -> handledIds.contains(menu.getId()));
        parentMenu.setHasChildren(Boolean.TRUE);
        if (parentMenu.getChildren().size() > 0) {
            parentMenu.setHasChildren(Boolean.FALSE);
            for (SystemMenuListVO child : parentMenu.getChildren()) {
                this.fillChildren(child, menus);
            }
        }
    }

    /**
     * 填充子菜单
     * @author Eva
     * @date 2021-03-29 16:09
     */
    private void fillChildren(SystemMenuNodeVO parentNode, List<SystemMenu> menus) {
        if (menus.size() == 0) {
            return;
        }
        List<Integer> handledIds = new ArrayList<>();
        for (SystemMenu menu : menus) {
            if (menu.getParentId().equals(parentNode.getId())) {
                SystemMenuNodeVO child = new SystemMenuNodeVO();
                child.setId(menu.getId());
                child.setLabel(menu.getName());
                child.setUrl(menu.getPath());
                child.setIcon(menu.getIcon());
                child.setIndex("menu_" + menu.getId());
                child.setChildren(new ArrayList<>());
                parentNode.getChildren().add(child);
                handledIds.add(menu.getId());
            }
        }
        menus.removeIf(menu -> handledIds.contains(menu.getId()));
        for (SystemMenuNodeVO child : parentNode.getChildren()) {
            this.fillChildren(child, menus);
        }
    }
}
