package com.eva.service.system;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.dto.QuerySystemPermissionDTO;
import com.eva.dao.system.model.SystemPermission;
import com.eva.dao.system.vo.SystemPermissionListVO;

import java.util.List;

/**
 * 系统权限Service定义
 * @author Caesar Liu
 * @date 2021/05/15 19:41
 */
public interface SystemPermissionService {

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    Integer create(SystemPermission systemPermission);

    /**
     * 主键删除
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    void deleteById(Integer id);

    /**
     * 批量主键删除
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    void updateById(SystemPermission systemPermission);

    /**
     * 批量主键更新
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    void updateByIdInBatch(List<SystemPermission> systemPermissions);

    /**
     * 主键查询
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    SystemPermission findById(Integer id);

    /**
     * 根据用户ID查询
     * @author Caesar Liu
     * @date 2021-03-30 23:15
     */
    List<SystemPermission> findByUserId(Integer userId);

    /**
     * 根据角色ID查询
     * @author Caesar Liu
     * @date 2021-03-30 23:15
     */
    List<SystemPermission> findByRoleId(Integer roleId);

    /**
     * 条件查询单条记录
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    SystemPermission findOne(SystemPermission systemPermission);

    /**
     * 条件查询
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    List<SystemPermission> findList(SystemPermission systemPermission);
  
    /**
     * 分页查询
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    PageData<SystemPermissionListVO> findPage(PageWrap<QuerySystemPermissionDTO> pageWrap);

    /**
     * 条件统计
     * @author Caesar Liu
     * @date 2021/05/15 19:41
     */
    long count(SystemPermission systemPermission);
}