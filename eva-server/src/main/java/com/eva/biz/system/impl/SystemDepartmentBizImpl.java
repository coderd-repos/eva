package com.eva.biz.system.impl;

import com.eva.biz.system.SystemDepartmentBiz;
import com.eva.core.exception.BusinessException;
import com.eva.core.constants.ResponseStatus;
import com.eva.dao.system.model.SystemDepartment;
import com.eva.dao.system.vo.SystemDepartmentListVO;
import com.eva.service.aware.DepartmentDataPermissionAware;
import com.eva.service.system.SystemDepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemDepartmentBizImpl implements SystemDepartmentBiz {

    @Autowired
    private SystemDepartmentService systemDepartmentService;

    @Autowired
    private DepartmentDataPermissionAware departmentDataPermissionAware;

    @Override
    public Integer create(SystemDepartment department) {
        // 验证部门编码
        SystemDepartment queryDto = new SystemDepartment();
        queryDto.setCode(department.getCode());
        queryDto.setDeleted(Boolean.FALSE);
        SystemDepartment systemDepartment = systemDepartmentService.findOne(queryDto);
        if (systemDepartment != null) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "部门编码已存在");
        }
        return systemDepartmentService.create(department);
    }

    @Override
    public void updateById(SystemDepartment department) {
        // 验证部门编码
        SystemDepartment queryDto = new SystemDepartment();
        queryDto.setCode(department.getCode());
        queryDto.setDeleted(Boolean.FALSE);
        SystemDepartment systemDepartment = systemDepartmentService.findOne(queryDto);
        if (systemDepartment != null && !systemDepartment.getId().equals(department.getId())) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "部门编码已存在");
        }
        systemDepartmentService.updateById(department);
    }

    @Override
    public List<SystemDepartmentListVO> findTree() {
        return departmentDataPermissionAware.execute("DEPARTMENT");
//        List<SystemDepartmentListVO> departments = systemDepartmentService.findList();
//        List<SystemDepartmentListVO> rootDepartments = new ArrayList<>();
//        // 添加根菜单
//        for (SystemDepartmentListVO department : departments) {
//            if (department.getParentId() == null) {
//                SystemDepartmentListVO rootMenu = new SystemDepartmentListVO();
//                BeanUtils.copyProperties(department, rootMenu, "children");
//                rootMenu.setChildren(new ArrayList<>());
//                rootDepartments.add(rootMenu);
//            }
//        }
//        departments.removeIf(department -> department.getParentId() == null);
//        for (SystemDepartmentListVO child : rootDepartments) {
//            this.fillChildren(child, departments);
//        }
//        return rootDepartments;
    }

    @Override
    public void deleteById(Integer id) {
        List<Integer> ids = systemDepartmentService.findChildren(id);
        ids.add(id);
        systemDepartmentService.deleteByIdInBatch(ids);
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
     * 填充子部门
     * @author Eva.Caesar Liu
     * @date 2021-03-29 16:09
     */
    private void fillChildren(SystemDepartmentListVO parent, List<SystemDepartmentListVO> departments) {
        if (departments.size() == 0) {
            return;
        }
        List<Integer> handledIds = new ArrayList<>();
        for (SystemDepartmentListVO department : departments) {
            if (parent.getId().equals(department.getParentId())) {
                SystemDepartmentListVO child = new SystemDepartmentListVO();
                BeanUtils.copyProperties(department, child, "children");
                child.setChildren(new ArrayList<>());
                parent.getChildren().add(child);
                handledIds.add(department.getId());
            }
        }
        departments.removeIf(menu -> handledIds.contains(menu.getId()));
        parent.setHasChildren(Boolean.TRUE);
        if (parent.getChildren().size() > 0) {
            parent.setHasChildren(Boolean.FALSE);
            for (SystemDepartmentListVO child : parent.getChildren()) {
                this.fillChildren(child, departments);
            }
        }
    }
}
