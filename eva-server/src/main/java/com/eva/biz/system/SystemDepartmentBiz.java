package com.eva.biz.system;

import com.eva.dao.system.model.SystemDepartment;
import com.eva.dao.system.vo.SystemDepartmentListVO;

import java.util.List;

/**
 * 部门管理业务处理
 * @author Eva
 * @date 2021-05-16 15:30
 */
public interface SystemDepartmentBiz {

    /**
     * 新建
     * @author Eva
     * @date 2021-05-16 16:16
     */
    Integer create(SystemDepartment department);

    /**
     * 编辑
     * @author Eva
     * @date 2021-05-22 14:08
     */
    void updateById(SystemDepartment department);

    /**
     * 查询部门
     * @author Eva
     * @date 2021-05-16 15:29
     */
    List<SystemDepartmentListVO> findList();
}