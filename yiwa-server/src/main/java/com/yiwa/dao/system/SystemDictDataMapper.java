package com.yiwa.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiwa.dao.system.dto.QuerySystemDictDataDTO;
import com.yiwa.dao.system.model.SystemDictData;
import com.yiwa.dao.system.vo.SystemDictDataListVO;

import java.util.List;

public interface SystemDictDataMapper extends BaseMapper<SystemDictData> {

    /**
     * 查询字典数据管理列表
     * @author Caesar Liu
     * @date 2021-05-16 20:48
     */
    List<SystemDictDataListVO> selectManageList(QuerySystemDictDataDTO dto);

}