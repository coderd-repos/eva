package com.yiwa.service.system;

import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.dto.QuerySystemDictDataDTO;
import com.yiwa.dao.system.model.SystemDictData;
import com.yiwa.dao.system.vo.SystemDictDataListVO;

import java.util.List;

/**
 * 字典数据Service定义
 * @author Caesar Liu
 * @date 2021/05/16 20:18
 */
public interface SystemDictDataService {

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/05/16 20:18
     */
    Integer create(SystemDictData systemDictData);

    /**
     * 主键删除
     * @author Caesar Liu
     * @date 2021/05/16 20:18
     */
    void deleteById(Integer id);

    /**
     * 批量主键删除
     * @author Caesar Liu
     * @date 2021/05/16 20:18
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Caesar Liu
     * @date 2021/05/16 20:18
     */
    void updateById(SystemDictData systemDictData);

    /**
     * 批量主键更新
     * @author Caesar Liu
     * @date 2021/05/16 20:18
     */
    void updateByIdInBatch(List<SystemDictData> systemDictDatas);

    /**
     * 主键查询
     * @author Caesar Liu
     * @date 2021/05/16 20:18
     */
    SystemDictData findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Caesar Liu
     * @date 2021/05/16 20:18
     */
    SystemDictData findOne(SystemDictData systemDictData);

    /**
     * 条件查询
     * @author Caesar Liu
     * @date 2021/05/16 20:18
     */
    List<SystemDictData> findList(SystemDictData systemDictData);
  
    /**
     * 分页查询
     * @author Caesar Liu
     * @date 2021/05/16 20:18
     */
    PageData<SystemDictDataListVO> findPage(PageWrap<QuerySystemDictDataDTO> pageWrap);

    /**
     * 条件统计
     * @author Caesar Liu
     * @date 2021/05/16 20:18
     */
    long count(SystemDictData systemDictData);
}