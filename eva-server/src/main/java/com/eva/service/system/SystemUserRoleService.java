package com.eva.service.system;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.model.SystemUserRole;
import java.util.List;

/**
 * 用户角色关联Service定义
 * @author Eva.Caesar Liu
 * @date 2021/05/15 19:41
 */
public interface SystemUserRoleService {

    /**
     * 创建
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    Integer create(SystemUserRole systemUserRole);

    /**
     * 主键删除
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    void deleteById(Integer id);

    /**
     * 删除
     * @author Eva.Caesar Liu
     * @date 2021-03-29 22:26
     */
    void delete(SystemUserRole systemUserRole);

    /**
     * 批量主键删除
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    void updateById(SystemUserRole systemUserRole);

    /**
     * 批量主键更新
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    void updateByIdInBatch(List<SystemUserRole> systemUserRoles);

    /**
     * 主键查询
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    SystemUserRole findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    SystemUserRole findOne(SystemUserRole systemUserRole);

    /**
     * 条件查询
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    List<SystemUserRole> findList(SystemUserRole systemUserRole);
  
    /**
     * 分页查询
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    PageData<SystemUserRole> findPage(PageWrap<SystemUserRole> pageWrap);

    /**
     * 条件统计
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    long count(SystemUserRole systemUserRole);
}