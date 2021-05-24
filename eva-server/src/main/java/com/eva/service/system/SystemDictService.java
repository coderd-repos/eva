package com.eva.service.system;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.QuerySystemDictDTO;
import com.eva.dao.system.model.SystemDict;
import com.eva.dao.system.vo.SystemDictListVO;

import java.util.List;

/**
 * 字典Service定义
 * @author Eva
 * @date 2021/05/16 17:40
 */
public interface SystemDictService {

    /**
     * 创建
     * @author Eva
     * @date 2021/05/16 17:40
     */
    Integer create(SystemDict systemDict);

    /**
     * 主键删除
     * @author Eva
     * @date 2021/05/16 17:40
     */
    void deleteById(Integer id);

    /**
     * 批量主键删除
     * @author Eva
     * @date 2021/05/16 17:40
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Eva
     * @date 2021/05/16 17:40
     */
    void updateById(SystemDict systemDict);

    /**
     * 批量主键更新
     * @author Eva
     * @date 2021/05/16 17:40
     */
    void updateByIdInBatch(List<SystemDict> systemDicts);

    /**
     * 主键查询
     * @author Eva
     * @date 2021/05/16 17:40
     */
    SystemDict findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Eva
     * @date 2021/05/16 17:40
     */
    SystemDict findOne(SystemDict systemDict);

    /**
     * 条件查询
     * @author Eva
     * @date 2021/05/16 17:40
     */
    List<SystemDict> findList(SystemDict systemDict);
  
    /**
     * 分页查询
     * @author Eva
     * @date 2021/05/16 17:40
     */
    PageData<SystemDictListVO> findPage(PageWrap<QuerySystemDictDTO> pageWrap);

    /**
     * 条件统计
     * @author Eva
     * @date 2021/05/16 17:40
     */
    long count(SystemDict systemDict);
}