package com.eva.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eva.dao.system.dto.QuerySystemDictDTO;
import com.eva.dao.system.model.SystemDict;
import com.eva.dao.system.vo.SystemDictListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemDictMapper extends BaseMapper<SystemDict> {

    /**
     * 查询字典管理列表
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    List<SystemDictListVO> selectManageList(@Param("dto") QuerySystemDictDTO dto, @Param("orderByClause") String orderByClause);
}
