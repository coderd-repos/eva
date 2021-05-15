package com.yiwa.service.system;

import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.system.model.SystemMenu;
import java.util.List;

/**
 * 系统菜单Service定义
 * @author Caesar Liu
 * @date 2021/05/15 19:40
 */
public interface SystemMenuService {

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/05/15 19:40
     */
    Integer create(SystemMenu systemMenu);

    /**
     * 主键删除
     * @author Caesar Liu
     * @date 2021/05/15 19:40
     */
    void deleteById(Integer id);

    /**
     * 批量主键删除
     * @author Caesar Liu
     * @date 2021/05/15 19:40
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Caesar Liu
     * @date 2021/05/15 19:40
     */
    void updateById(SystemMenu systemMenu);

    /**
     * 批量主键更新
     * @author Caesar Liu
     * @date 2021/05/15 19:40
     */
    void updateByIdInBatch(List<SystemMenu> systemMenus);

    /**
     * 主键查询
     * @author Caesar Liu
     * @date 2021/05/15 19:40
     */
    SystemMenu findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Caesar Liu
     * @date 2021/05/15 19:40
     */
    SystemMenu findOne(SystemMenu systemMenu);

    /**
     * 条件查询
     * @author Caesar Liu
     * @date 2021/05/15 19:40
     */
    List<SystemMenu> findList(SystemMenu systemMenu);
  
    /**
     * 分页查询
     * @author Caesar Liu
     * @date 2021/05/15 19:40
     */
    PageData<SystemMenu> findPage(PageWrap<SystemMenu> pageWrap);

    /**
     * 条件统计
     * @author Caesar Liu
     * @date 2021/05/15 19:40
     */
    long count(SystemMenu systemMenu);
}