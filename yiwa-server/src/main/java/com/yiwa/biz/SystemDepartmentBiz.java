package com.yiwa.biz;

import com.yiwa.dao.system.model.SystemDepartment;
import com.yiwa.dao.system.vo.SystemDepartmentListVO;

import java.util.List;

/**
 * 部门管理业务处理
 * @author Caesar Liu
 * @date 2021-05-16 15:30
 */
public interface SystemDepartmentBiz {

    /**
     * 创建部门
     * @author Caesar Liu
     * @date 2021-05-16 16:16
     */
    Integer create(SystemDepartment department);

    /**
     * 查询部门
     * @author Caesar Liu
     * @date 2021-05-16 15:29
     */
    List<SystemDepartmentListVO> findList();
}
