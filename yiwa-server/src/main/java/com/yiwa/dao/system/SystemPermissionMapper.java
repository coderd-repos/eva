package com.yiwa.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiwa.dao.system.dto.QuerySystemPermissionDTO;
import com.yiwa.dao.system.model.SystemPermission;
import com.yiwa.dao.system.vo.SystemPermissionListVO;

import java.util.List;

public interface SystemPermissionMapper extends BaseMapper<SystemPermission> {

    /**
     * 根据用户ID查询
     * @author Caesar Liu
     * @date 2021-03-30 23:19
     */
    List<SystemPermission> selectByUserId(Integer userId);

    /**
     * 根据角色ID查询
     * @author Caesar Liu
     * @date 2021-03-31 20:43
     */
    List<SystemPermission> selectByRoleId(Integer roleId);

    /**
     * 查询列表
     * @author Caesar Liu
     * @date 2021-03-31 15:15
     */
    List<SystemPermissionListVO> selectManageList(QuerySystemPermissionDTO dto);

}