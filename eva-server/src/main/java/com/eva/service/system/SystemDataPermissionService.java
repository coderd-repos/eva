package com.eva.service.system;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.model.SystemDataPermission;
import com.eva.dao.system.vo.SystemDataPermissionListVO;

import java.util.List;

/**
 * 数据权限配置Service定义
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
public interface SystemDataPermissionService {

    /**
     * 创建
     * 
     * @param systemDataPermission 实体对象
     * @return Integer
     */
    Integer create(SystemDataPermission systemDataPermission);

    /**
     * 主键删除
     *
     * @param id 主键
     */
    void deleteById(Integer id);

    /**
     * 删除
     *
     * @param systemDataPermission 实体对象
     */
    void delete(SystemDataPermission systemDataPermission);

    /**
     * 批量主键删除
     *
     * @param ids 主键集
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     *
     * @param systemDataPermission 实体对象
     */
    void updateById(SystemDataPermission systemDataPermission);

    /**
     * 批量主键更新
     *
     * @param systemDataPermissions 实体集
     */
    void updateByIdInBatch(List<SystemDataPermission> systemDataPermissions);

    /**
     * 主键查询
     *
     * @param id 主键
     * @return SystemDataPermission
     */
    SystemDataPermission findById(Integer id);

    /**
     * 条件查询单条记录
     *
     * @param systemDataPermission 实体对象
     * @return SystemDataPermission
     */
    SystemDataPermission findOne(SystemDataPermission systemDataPermission);

    /**
     * 条件查询
     *
     * @param systemDataPermission 实体对象
     * @return List<SystemDataPermission>
     */
    List<SystemDataPermission> findList(SystemDataPermission systemDataPermission);
  
    /**
     * 分页查询
     *
     * @param pageWrap 分页对象
     * @return PageData<SystemDataPermission>
     */
    PageData<SystemDataPermissionListVO> findPage(PageWrap<SystemDataPermission> pageWrap);

    /**
     * 查询数据权限
     *
     * @param businessCode 业务模块编码
     * @param roles 角色编码
     * @return List<SystemDataPermission>
     */
    List<SystemDataPermission> findDataPermission(String businessCode, List<String> roles);

    /**
     * 条件统计
     *
     * @param systemDataPermission 实体对象
     * @return long
     */
    long count(SystemDataPermission systemDataPermission);
}
