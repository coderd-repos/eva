package com.eva.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eva.dao.system.model.SystemMenu;
import com.eva.dao.system.vo.SystemMenuListVO;

import java.util.List;

public interface SystemMenuMapper extends BaseMapper<SystemMenu> {

    /**
     * 查询列表
     * @author Eva
     * @date 2021-03-30 20:16
     */
    List<SystemMenuListVO> selectManageList();

    /**
     * 根据用户ID查询
     * @author Eva
     * @date 2021-03-30 16:22
     */
    List<SystemMenu> selectByUserId(Integer userId);

    /**
     * 根据角色ID查询
     * @author Eva
     * @date 2021-03-31 20:40
     */
    List<SystemMenu> selectByRoleId(Integer roleId);

}