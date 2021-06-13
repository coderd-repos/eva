package com.eva.service.aware;

import com.eva.core.aware.DataPermissionMapping;
import com.eva.core.aware.DefaultPaginationDataPermissionAware;
import com.eva.core.constants.DataPermissionConstants;
import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.QuerySystemUserDTO;
import com.eva.dao.system.model.SystemDepartment;
import com.eva.dao.system.model.SystemDepartmentUser;
import com.eva.dao.system.vo.SystemDepartmentListVO;
import com.eva.dao.system.vo.SystemUserListVO;
import com.eva.service.system.SystemDepartmentService;
import com.eva.service.system.SystemDepartmentUserService;
import com.eva.service.system.SystemUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 用户权限控制
 * @author Caesar Liu
 * @date 2021-06-13 01:54
 */
@Component
public class UserDataPermissionAware extends DefaultPaginationDataPermissionAware<SystemUserListVO, QuerySystemUserDTO> {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemDepartmentService systemDepartmentService;

    @Autowired
    private SystemDepartmentUserService systemDepartmentUserService;

    @Override
    public PageData<SystemUserListVO> all(PageWrap<QuerySystemUserDTO> pageWrap) {
        return systemUserService.findPage(pageWrap);
    }

    @Override
    public DataPermissionConstants.Module getModule() {
        return DataPermissionConstants.Module.USER;
    }

    /**
     * 查询部门及其子孙部门用户
     */
    @DataPermissionMapping(value = DataPermissionConstants.Type.DEPARTMENT_CHILDREN, priority = 1, injectUser = true)
    public PageData<SystemUserListVO> departmentChildren (PageWrap<QuerySystemUserDTO> pageWrap, Integer userId) {
        List<SystemDepartment> departments = getUserDepartmentChildren(userId);
        if (CollectionUtils.isEmpty(departments)) {
            return PageData.from(new PageInfo<>(Collections.emptyList()));
        }
        List<Integer> departmentIds = new ArrayList<>();
        for (SystemDepartment systemDepartment : departments) {
            departmentIds.add(systemDepartment.getId());
        }
        pageWrap.getModel().setDepartmentIds(departmentIds);
        return systemUserService.findPage(pageWrap);
    }

    /**
     * 获取用户部门
     */
    private SystemDepartment getUserDepartment (Integer userId) {
        SystemDepartmentUser queryDto = new SystemDepartmentUser();
        queryDto.setUserId(userId);
        queryDto.setDeleted(Boolean.FALSE);
        SystemDepartmentUser departmentUser = systemDepartmentUserService.findOne(queryDto);
        if (departmentUser == null) {
            return null;
        }
        return systemDepartmentService.findById(departmentUser.getDepartmentId());
    }

    /**
     * 获取用户子孙部门
     */
    private List<SystemDepartment> getUserDepartmentChildren (Integer userId) {
        SystemDepartment userDepartment = this.getUserDepartment(userId);
        if (userDepartment == null) {
            return Collections.emptyList();
        }
        // 查询用户所在部门以下部门
        List<SystemDepartment> departments = systemDepartmentService.findByIds(systemDepartmentService.findChildren(userDepartment.getId()));
        departments.add(userDepartment);
        return departments;
    }
}
