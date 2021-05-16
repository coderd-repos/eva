package com.yiwa.dao.system.vo;

import com.yiwa.dao.system.model.SystemDepartment;
import com.yiwa.dao.system.model.SystemUser;
import lombok.Data;

import java.util.List;

/**
 * 部门列表视图对象
 * @author Caesar Liu
 * @date 2021-05-16 15:27
 */
@Data
public class SystemDepartmentListVO extends SystemDepartment {

    private List<SystemDepartmentListVO> children;

    private Boolean hasChildren;

    private SystemUser createUserInfo;

    private SystemUser updateUserInfo;
}
