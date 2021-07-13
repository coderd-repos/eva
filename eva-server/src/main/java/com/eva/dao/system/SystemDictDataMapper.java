package com.eva.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eva.dao.system.dto.QuerySystemDictDataDTO;
import com.eva.dao.system.model.SystemDictData;
import com.eva.dao.system.vo.SystemDictDataListVO;

import java.util.List;

public interface SystemDictDataMapper extends BaseMapper<SystemDictData> {

    /**
     * 查询字典数据管理列表
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    List<SystemDictDataListVO> selectManageList(QuerySystemDictDataDTO dto);

}
