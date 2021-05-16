package com.yiwa.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiwa.dao.system.dto.QuerySystemPositionDTO;
import com.yiwa.dao.system.model.SystemPosition;
import com.yiwa.dao.system.vo.SystemPositionListVO;

import java.util.List;

public interface SystemPositionMapper extends BaseMapper<SystemPosition> {

    /**
     * @author Caesar Liu
     * @date 2021-05-16 17:18
     */
    List<SystemPositionListVO> selectList(QuerySystemPositionDTO dto);
}