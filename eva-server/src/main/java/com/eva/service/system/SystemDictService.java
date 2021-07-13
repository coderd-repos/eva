package com.eva.service.system;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.QuerySystemDictDTO;
import com.eva.dao.system.model.SystemDict;
import com.eva.dao.system.vo.SystemDictListVO;

import java.util.List;

/**
 * 字典Service定义
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
public interface SystemDictService {

    /**
     * 创建
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    Integer create(SystemDict systemDict);

    /**
     * 主键删除
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    void deleteById(Integer id);

    /**
     * 批量主键删除
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    void updateById(SystemDict systemDict);

    /**
     * 批量主键更新
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    void updateByIdInBatch(List<SystemDict> systemDicts);

    /**
     * 主键查询
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    SystemDict findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    SystemDict findOne(SystemDict systemDict);

    /**
     * 条件查询
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    List<SystemDict> findList(SystemDict systemDict);
  
    /**
     * 分页查询
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    PageData<SystemDictListVO> findPage(PageWrap<QuerySystemDictDTO> pageWrap);

    /**
     * 条件统计
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    long count(SystemDict systemDict);
}
