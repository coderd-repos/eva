package com.eva.biz.system;

import com.eva.dao.system.dto.UpdateSystemMenuSortDTO;
import com.eva.dao.system.model.SystemMenu;
import com.eva.dao.system.vo.SystemMenuListVO;
import com.eva.dao.system.vo.SystemMenuNodeVO;

import java.util.List;

/**
 * 系统菜单业务处理
 * @author Eva
 * @date 2021-03-30 10:51
 */
public interface SystemMenuBiz {

    /**
     * 创建
     * @author Eva
     * @date 2021-05-17 11:51
     */
    Integer create(SystemMenu systemMenu);

    /**
     * 菜单排序
     * @author Eva
     * @date 2021-03-30 21:48
     */
    void updateSort(UpdateSystemMenuSortDTO dto);

    /**
     * 查询菜单节点列表
     * @author Eva
     * @date 2021-03-29 20:24
     */
    List<SystemMenuNodeVO> findTree(Integer userId);

    /**
     * 分页查询菜单
     * @author Eva
     * @date 2021-03-29 20:19
     */
    List<SystemMenuListVO> findList();
}