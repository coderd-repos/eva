package com.eva.service.system;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;
import com.eva.dao.system.model.SystemRoleMenu;
import java.util.List;

/**
 * 角色菜单关联Service定义
 * @author Eva
 * @date 2021/05/15 19:41
 */
public interface SystemRoleMenuService {

    /**
     * 创建
     * @author Eva
     * @date 2021/05/15 19:41
     */
    Integer create(SystemRoleMenu systemRoleMenu);

    /**
     * 主键删除
     * @author Eva
     * @date 2021/05/15 19:41
     */
    void deleteById(Integer id);

    /**
     * 删除
     * @author Eva
     * @date 2021-03-30 15:25
     */
    void delete(SystemRoleMenu systemRoleMenu);

    /**
     * 批量主键删除
     * @author Eva
     * @date 2021/05/15 19:41
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Eva
     * @date 2021/05/15 19:41
     */
    void updateById(SystemRoleMenu systemRoleMenu);

    /**
     * 批量主键更新
     * @author Eva
     * @date 2021/05/15 19:41
     */
    void updateByIdInBatch(List<SystemRoleMenu> systemRoleMenus);

    /**
     * 主键查询
     * @author Eva
     * @date 2021/05/15 19:41
     */
    SystemRoleMenu findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Eva
     * @date 2021/05/15 19:41
     */
    SystemRoleMenu findOne(SystemRoleMenu systemRoleMenu);

    /**
     * 条件查询
     * @author Eva
     * @date 2021/05/15 19:41
     */
    List<SystemRoleMenu> findList(SystemRoleMenu systemRoleMenu);
  
    /**
     * 分页查询
     * @author Eva
     * @date 2021/05/15 19:41
     */
    PageData<SystemRoleMenu> findPage(PageWrap<SystemRoleMenu> pageWrap);

    /**
     * 条件统计
     * @author Eva
     * @date 2021/05/15 19:41
     */
    long count(SystemRoleMenu systemRoleMenu);
}