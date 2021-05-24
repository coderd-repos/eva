package com.eva.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eva.dao.system.dto.QuerySystemUserDTO;
import com.eva.dao.system.model.SystemUser;
import com.eva.dao.system.vo.SystemUserListVO;

import java.util.List;

public interface SystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 查询用户列表
     * @author Eva
     * @date 2021-03-29 22:52
     */
    List<SystemUserListVO> selectManageList(QuerySystemUserDTO dto);

}