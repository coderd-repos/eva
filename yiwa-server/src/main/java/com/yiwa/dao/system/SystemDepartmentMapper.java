package com.yiwa.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiwa.dao.system.model.SystemDepartment;
import com.yiwa.dao.system.vo.SystemDepartmentListVO;

import java.util.List;

public interface SystemDepartmentMapper extends BaseMapper<SystemDepartment> {

    /**
     * 查询部门
     * @author Yiwa
     * @date 2021-05-16 15:40
     */
    List<SystemDepartmentListVO> selectManageList();
}