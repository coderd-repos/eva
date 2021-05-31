package com.eva.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eva.dao.system.dto.QuerySystemRoleDTO;
import com.eva.dao.system.model.SystemRole;
import com.eva.dao.system.vo.SystemRoleListVO;

import java.util.List;

public interface SystemRoleMapper extends BaseMapper<SystemRole> {

    /**
     * 查询角色列表
     * @author Eva.Caesar Liu
     * @date 2021-03-30 11:56
     */
    List<SystemRoleListVO> selectManageList(QuerySystemRoleDTO dto);

    /**
     * 根据用户ID查询
     * @author Eva.Caesar Liu
     * @date 2021-03-31 21:04
     */
    List<SystemRole> selectByUserId(Integer userId);

}