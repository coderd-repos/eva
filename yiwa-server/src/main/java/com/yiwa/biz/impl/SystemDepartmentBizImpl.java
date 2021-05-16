package com.yiwa.biz.impl;

import com.yiwa.biz.SystemDepartmentBiz;
import com.yiwa.dao.system.model.SystemDepartment;
import com.yiwa.dao.system.vo.SystemDepartmentListVO;
import com.yiwa.service.system.SystemDepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemDepartmentBizImpl implements SystemDepartmentBiz {

    @Autowired
    private SystemDepartmentService systemDepartmentService;

    @Override
    public Integer create(SystemDepartment department) {
        // 统计上级部门下部门数量
        SystemDepartment countDto = new SystemDepartment();
        countDto.setParentId(department.getParentId());
        long subDeptCount = systemDepartmentService.count(countDto);
        // 设置新建部门的顺序
        department.setSort(Integer.valueOf("" + subDeptCount));
        return systemDepartmentService.create(department);
    }

    @Override
    public List<SystemDepartmentListVO> findList() {
        List<SystemDepartmentListVO> departments = systemDepartmentService.findList();
        List<SystemDepartmentListVO> rootMenus = new ArrayList<>();
        // 添加根菜单
        for (SystemDepartmentListVO menu : departments) {
            if (menu.getParentId() == null) {
                SystemDepartmentListVO rootMenu = new SystemDepartmentListVO();
                BeanUtils.copyProperties(menu, rootMenu, "children");
                rootMenu.setChildren(new ArrayList<>());
                rootMenus.add(rootMenu);
            }
        }
        departments.removeIf(menu -> menu.getParentId() == null);
        for (SystemDepartmentListVO child : rootMenus) {
            this.fillChildren(child, departments);
        }
        return rootMenus;
    }

    /**
     * 填充子部门
     * @author Caesar Liu
     * @date 2021-03-29 16:09
     */
    private void fillChildren(SystemDepartmentListVO parentMenu, List<SystemDepartmentListVO> departments) {
        if (departments.size() == 0) {
            return;
        }
        List<Integer> handledIds = new ArrayList<>();
        for (SystemDepartmentListVO department : departments) {
            if (department.getParentId().equals(parentMenu.getId())) {
                SystemDepartmentListVO child = new SystemDepartmentListVO();
                BeanUtils.copyProperties(department, child, "children");
                child.setChildren(new ArrayList<>());
                parentMenu.getChildren().add(child);
                handledIds.add(department.getId());
            }
        }
        departments.removeIf(menu -> handledIds.contains(menu.getId()));
        parentMenu.setHasChildren(Boolean.TRUE);
        if (parentMenu.getChildren().size() > 0) {
            parentMenu.setHasChildren(Boolean.FALSE);
            for (SystemDepartmentListVO child : parentMenu.getChildren()) {
                this.fillChildren(child, departments);
            }
        }
    }
}
