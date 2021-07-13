package com.eva.biz.system;

import com.eva.dao.system.dto.UpdateSystemMenuSortDTO;
import com.eva.dao.system.model.SystemMenu;
import com.eva.dao.system.vo.SystemMenuListVO;
import com.eva.dao.system.vo.SystemMenuNodeVO;

import java.util.List;

/**
 * 系统菜单业务处理
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
public interface SystemMenuBiz {

    /**
     * 创建
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    Integer create(SystemMenu systemMenu);

    /**
     * 编辑
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    void updateById(SystemMenu systemMenu);

    /**
     * 菜单排序
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    void updateSort(UpdateSystemMenuSortDTO dto);

    /**
     * 查询菜单节点列表
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    List<SystemMenuNodeVO> findTree(Integer userId);

    /**
     * 查询菜单列表树
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    List<SystemMenuListVO> findTree();

    /**
     * 删除
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    void deleteById(Integer id);

    /**
     * 批量删除
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    void deleteByIdInBatch(List<Integer> ids);
}
