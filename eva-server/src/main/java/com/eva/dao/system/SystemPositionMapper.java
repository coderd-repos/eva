package com.eva.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eva.dao.system.model.SystemPosition;
import com.eva.dao.system.vo.SystemPositionListVO;

import java.util.List;

public interface SystemPositionMapper extends BaseMapper<SystemPosition> {

    /**
     * @author Eva
     * @date 2021-05-16 17:18
     */
    List<SystemPositionListVO> selectManageList();
}