package com.eva.service.system;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.model.SystemTraceLog;
import java.util.List;

/**
 * 跟踪日志Service定义
 * @author Eva
 * @date 2021/05/29 13:53
 */
public interface SystemTraceLogService {

    /**
     * 创建
     * @author Eva
     * @date 2021/05/29 13:53
     */
    Integer create(SystemTraceLog systemTraceLog);

    /**
     * 主键删除
     * @author Eva
     * @date 2021/05/29 13:53
     */
    void deleteById(Integer id);

    /**
     * 批量主键删除
     * @author Eva
     * @date 2021/05/29 13:53
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Eva
     * @date 2021/05/29 13:53
     */
    void updateById(SystemTraceLog systemTraceLog);

    /**
     * 批量主键更新
     * @author Eva
     * @date 2021/05/29 13:53
     */
    void updateByIdInBatch(List<SystemTraceLog> systemTraceLogs);

    /**
     * 主键查询
     * @author Eva
     * @date 2021/05/29 13:53
     */
    SystemTraceLog findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Eva
     * @date 2021/05/29 13:53
     */
    SystemTraceLog findOne(SystemTraceLog systemTraceLog);

    /**
     * 条件查询
     * @author Eva
     * @date 2021/05/29 13:53
     */
    List<SystemTraceLog> findList(SystemTraceLog systemTraceLog);
  
    /**
     * 分页查询
     * @author Eva
     * @date 2021/05/29 13:53
     */
    PageData<SystemTraceLog> findPage(PageWrap<SystemTraceLog> pageWrap);

    /**
     * 条件统计
     * @author Eva
     * @date 2021/05/29 13:53
     */
    long count(SystemTraceLog systemTraceLog);
}