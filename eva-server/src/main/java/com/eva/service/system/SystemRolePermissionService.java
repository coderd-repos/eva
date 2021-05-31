package com.eva.service.system;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.model.SystemRolePermission;
import java.util.List;

/**
 * 角色权限关联Service定义
 * @author Eva.Caesar Liu
 * @date 2021/05/15 19:41
 */
public interface SystemRolePermissionService {

    /**
     * 创建
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    Integer create(SystemRolePermission systemRolePermission);

    /**
     * 主键删除
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    void deleteById(Integer id);

    /**
     * 删除
     * @author Eva.Caesar Liu
     * @date 2021-03-30 10:53
     */
    void delete(SystemRolePermission systemRolePermission);

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
    void updateById(SystemRolePermission systemRolePermission);

    /**
     * 批量主键更新
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    void updateByIdInBatch(List<SystemRolePermission> systemRolePermissions);

    /**
     * 主键查询
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    SystemRolePermission findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    SystemRolePermission findOne(SystemRolePermission systemRolePermission);

    /**
     * 条件查询
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    List<SystemRolePermission> findList(SystemRolePermission systemRolePermission);
  
    /**
     * 分页查询
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    PageData<SystemRolePermission> findPage(PageWrap<SystemRolePermission> pageWrap);

    /**
     * 条件统计
     * @author Eva.Caesar Liu
     * @date 2021/05/15 19:41
     */
    long count(SystemRolePermission systemRolePermission);
}