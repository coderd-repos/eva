package com.eva.service.aware;

import com.eva.dao.system.model.SystemDepartment;
import com.eva.dao.system.model.SystemDepartmentUser;
import com.eva.dao.system.vo.SystemDepartmentListVO;
import com.eva.service.system.SystemDepartmentService;
import com.eva.service.system.SystemDepartmentUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 部门数据权限
 * @author Eva.Caesar Liu
 * @date 2021-06-11 21:16
 */
@Component
public class DepartmentDataPermissionAware extends DefaultDataPermissionAware<SystemDepartmentListVO> {

    @Autowired
    private SystemDepartmentService systemDepartmentService;

    @Autowired
    private SystemDepartmentUserService systemDepartmentUserService;

    @Override
    public List<SystemDepartmentListVO> all() {
        return this.getRootList(systemDepartmentService.findList());
    }

    @Override
    public List<SystemDepartmentListVO> custom(String customData) {
        List<Integer> ids = new ArrayList<>();
        String[] stringIds = customData.split(",");
        for(String stringId : stringIds) {
            ids.add(Integer.valueOf(stringId));
        }
        List<SystemDepartmentListVO> departmentListVo = new ArrayList<>();
        List<SystemDepartment> systemDepartments = systemDepartmentService.findByIds(ids);
        for (SystemDepartment systemDepartment : systemDepartments) {
            SystemDepartmentListVO vo = new SystemDepartmentListVO();
            BeanUtils.copyProperties(systemDepartment, vo);
            departmentListVo.add(vo);
        }
        return this.getRootList(departmentListVo);
    }

    @Override
    public List<SystemDepartmentListVO> user(Integer userId) {
        SystemDepartmentListVO userDepartment = this.getUserDepartment(userId);
        if (userDepartment == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(userDepartment);
    }

    @Override
    public List<SystemDepartmentListVO> userRelation(Integer userId) {
        SystemDepartmentListVO userDepartment = this.getUserDepartment(userId);
        if (userDepartment == null) {
            return Collections.emptyList();
        }
        // 查询用户部门以下部门
        List<SystemDepartmentListVO> departmentListVo = new ArrayList<>();
        List<SystemDepartment> systemDepartments = systemDepartmentService.findByIds(systemDepartmentService.findChildren(userDepartment.getId()));
        for (SystemDepartment systemDepartment : systemDepartments) {
            SystemDepartmentListVO vo = new SystemDepartmentListVO();
            BeanUtils.copyProperties(systemDepartment, vo);
            departmentListVo.add(vo);
        }
        departmentListVo.add(0, userDepartment);
        return this.getRootList(departmentListVo);
    }

    /**
     * 获取根节点
     */
    private List<SystemDepartmentListVO> getRootList(List<SystemDepartmentListVO> departments) {
        List<SystemDepartmentListVO> rootDepartments = new ArrayList<>();
        // 添加根部门
        for (SystemDepartmentListVO currentDepartment : departments) {
            boolean hasParent = false;
            for (SystemDepartmentListVO department: departments) {
                if (department.getId().equals(currentDepartment.getParentId())) {
                    hasParent = true;
                    break;
                }
            }
            if (!hasParent) {
                rootDepartments.add(currentDepartment);
            }
        }
        // 移除根部门
        for (SystemDepartmentListVO rootDepartment : rootDepartments) {
            departments.removeIf(department -> department.getId().equals(rootDepartment.getId()));
        }
        // 填充子部门
        for (SystemDepartmentListVO child : rootDepartments) {
            this.fillChildren(child, departments);
        }
        return rootDepartments;
    }

    /**
     * 获取用户部门
     */
    private SystemDepartmentListVO getUserDepartment (Integer userId) {
        SystemDepartmentUser queryDto = new SystemDepartmentUser();
        queryDto.setUserId(userId);
        queryDto.setDeleted(Boolean.FALSE);
        SystemDepartmentUser departmentUser = systemDepartmentUserService.findOne(queryDto);
        if (departmentUser == null) {
            return null;
        }
        SystemDepartment systemDepartment = systemDepartmentService.findById(departmentUser.getDepartmentId());
        SystemDepartmentListVO departmentListVO = new SystemDepartmentListVO();
        BeanUtils.copyProperties(systemDepartment, departmentListVO);
        return departmentListVO;
    }

    /**
     * 填充子部门
     */
    private void fillChildren(SystemDepartmentListVO parent, List<SystemDepartmentListVO> departments) {
        if (departments.size() == 0) {
            return;
        }
        if (parent.getChildren() == null) {
            parent.setChildren(new ArrayList<>());
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