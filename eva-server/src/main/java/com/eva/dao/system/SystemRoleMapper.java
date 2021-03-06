package com.eva.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eva.dao.system.dto.QuerySystemRoleDTO;
import com.eva.dao.system.model.SystemRole;
import com.eva.dao.system.vo.SystemRoleListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemRoleMapper extends BaseMapper<SystemRole> {

    /**
     * 查询角色列表
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    List<SystemRoleListVO> selectManageList(@Param("dto") QuerySystemRoleDTO dto, @Param("orderByClause") String orderByClause);

    /**
     * 根据用户ID查询
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    List<SystemRole> selectByUserId(Integer userId);

}
