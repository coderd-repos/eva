package com.eva.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eva.dao.system.model.SystemPosition;
import com.eva.dao.system.vo.SystemPositionListVO;

import java.util.List;

public interface SystemPositionMapper extends BaseMapper<SystemPosition> {

    /**
     * 查询管理列表
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    List<SystemPositionListVO> selectManageList();

    /**
     * 查询用户岗位列表
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    List<SystemPosition> selectByUserId(Integer userId);
}
