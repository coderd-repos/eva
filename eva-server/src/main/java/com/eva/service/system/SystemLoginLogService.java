package com.eva.service.system;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.QuerySystemLoginLogDTO;
import com.eva.dao.system.model.SystemLoginLog;
import java.util.List;

/**
 * 登录日志Service定义
 * @author Eva.Caesar Liu
 * @date 2021/05/30 22:54
 */
public interface SystemLoginLogService {

    /**
     * 创建
     * @author Eva.Caesar Liu
     * @date 2021/05/30 22:54
     */
    Integer create(SystemLoginLog systemLoginLog);

    /**
     * 主键删除
     * @author Eva.Caesar Liu
     * @date 2021/05/30 22:54
     */
    void deleteById(Integer id);

    /**
     * 删除
     * @author Eva.Caesar Liu
     * @date 2021/05/30 22:54
     */
    void delete(SystemLoginLog systemLoginLog);

    /**
     * 批量主键删除
     * @author Eva.Caesar Liu
     * @date 2021/05/30 22:54
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Eva.Caesar Liu
     * @date 2021/05/30 22:54
     */
    void updateById(SystemLoginLog systemLoginLog);

    /**
     * 批量主键更新
     * @author Eva.Caesar Liu
     * @date 2021/05/30 22:54
     */
    void updateByIdInBatch(List<SystemLoginLog> systemLoginLogs);

    /**
     * 主键查询
     * @author Eva.Caesar Liu
     * @date 2021/05/30 22:54
     */
    SystemLoginLog findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Eva.Caesar Liu
     * @date 2021/05/30 22:54
     */
    SystemLoginLog findOne(SystemLoginLog systemLoginLog);

    /**
     * 条件查询
     * @author Eva.Caesar Liu
     * @date 2021/05/30 22:54
     */
    List<SystemLoginLog> findList(SystemLoginLog systemLoginLog);
  
    /**
     * 分页查询
     * @author Eva.Caesar Liu
     * @date 2021/05/30 22:54
     */
    PageData<SystemLoginLog> findPage(PageWrap<QuerySystemLoginLogDTO> pageWrap);

    /**
     * 条件统计
     * @author Eva.Caesar Liu
     * @date 2021/05/30 22:54
     */
    long count(SystemLoginLog systemLoginLog);
}