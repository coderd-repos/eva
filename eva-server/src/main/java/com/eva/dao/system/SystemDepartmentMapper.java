package com.eva.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eva.dao.system.model.SystemDepartment;
import com.eva.dao.system.vo.SystemDepartmentListVO;

import java.util.List;

public interface SystemDepartmentMapper extends BaseMapper<SystemDepartment> {

    /**
     * 查询部门
     * @author Caesar Liu
     * @date 2021-05-16 15:40
     */
    List<SystemDepartmentListVO> selectManageList();
}