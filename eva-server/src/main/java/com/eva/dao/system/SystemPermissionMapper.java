package com.eva.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eva.dao.system.dto.QuerySystemPermissionDTO;
import com.eva.dao.system.model.SystemPermission;
import com.eva.dao.system.vo.SystemPermissionListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemPermissionMapper extends BaseMapper<SystemPermission> {

    /**
     * 根据用户ID查询
     * @author Eva
     * @date 2021-03-30 23:19
     */
    List<SystemPermission> selectByUserId(Integer userId);

    /**
     * 根据角色ID查询
     * @author Eva
     * @date 2021-03-31 20:43
     */
    List<SystemPermission> selectByRoleId(Integer roleId);

    /**
     * 查询列表
     * @author Eva
     * @date 2021-03-31 15:15
     */
    List<SystemPermissionListVO> selectManageList(@Param("dto") QuerySystemPermissionDTO dto, @Param("orderByClause") String orderByClause);

}