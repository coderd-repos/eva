package com.eva.service.system;

import com.eva.dao.system.model.SystemPositionUser;
import java.util.List;

/**
 * 岗位用户Service定义
 * @author Eva
 * @date 2021/05/22 09:35
 */
public interface SystemPositionUserService {

    /**
     * 创建
     * @author Eva
     * @date 2021/05/22 09:35
     */
    Integer create(SystemPositionUser systemPositionUser);

    /**
     * 主键删除
     * @author Eva
     * @date 2021/05/22 09:35
     */
    void deleteById(Integer id);

    /**
     * 删除
     * @author Eva
     * @date 2021-05-22 15:18
     */
    void delete(SystemPositionUser dto);

    /**
     * 批量主键删除
     * @author Eva
     * @date 2021/05/22 09:35
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Eva
     * @date 2021/05/22 09:35
     */
    void updateById(SystemPositionUser systemPositionUser);

    /**
     * 批量主键更新
     * @author Eva
     * @date 2021/05/22 09:35
     */
    void updateByIdInBatch(List<SystemPositionUser> systemPositionUsers);

    /**
     * 主键查询
     * @author Eva
     * @date 2021/05/22 09:35
     */
    SystemPositionUser findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Eva
     * @date 2021/05/22 09:35
     */
    SystemPositionUser findOne(SystemPositionUser systemPositionUser);

    /**
     * 条件查询
     * @author Eva
     * @date 2021/05/22 09:35
     */
    List<SystemPositionUser> findList(SystemPositionUser systemPositionUser);

    /**
     * 条件统计
     * @author Eva
     * @date 2021/05/22 09:35
     */
    long count(SystemPositionUser systemPositionUser);
}