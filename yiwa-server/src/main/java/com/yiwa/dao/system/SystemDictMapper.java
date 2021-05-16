package com.yiwa.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiwa.dao.system.dto.QuerySystemDictDTO;
import com.yiwa.dao.system.model.SystemDict;
import com.yiwa.dao.system.vo.SystemDictListVO;

import java.util.List;

public interface SystemDictMapper extends BaseMapper<SystemDict> {

    /**
     * 查询字典管理列表
     * @author Caesar Liu
     * @date 2021-05-16 20:10
     */
    List<SystemDictListVO> selectList(QuerySystemDictDTO dto);
}