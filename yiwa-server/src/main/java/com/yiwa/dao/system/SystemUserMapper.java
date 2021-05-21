package com.yiwa.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiwa.dao.system.dto.QuerySystemUserDTO;
import com.yiwa.dao.system.model.SystemUser;
import com.yiwa.dao.system.vo.SystemUserListVO;

import java.util.List;

public interface SystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 查询用户列表
     * @author Yiwa
     * @date 2021-03-29 22:52
     */
    List<SystemUserListVO> selectManageList(QuerySystemUserDTO dto);

}