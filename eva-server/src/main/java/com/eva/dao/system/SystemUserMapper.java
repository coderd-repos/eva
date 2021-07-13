package com.eva.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eva.dao.system.dto.QuerySystemUserDTO;
import com.eva.dao.system.model.SystemUser;
import com.eva.dao.system.vo.SystemUserListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 查询用户列表
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    List<SystemUserListVO> selectManageList(@Param("dto") QuerySystemUserDTO dto, @Param("orderByClause") String orderByClause);

}
