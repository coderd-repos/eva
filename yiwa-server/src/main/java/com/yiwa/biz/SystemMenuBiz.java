package com.yiwa.biz;

import com.admin.dao.system.dto.UpdateSystemMenuSortDTO;
import com.admin.dao.system.vo.SystemMenuListVO;
import com.admin.dao.system.vo.SystemMenuNodeVO;

import java.util.List;

/**
 * 系统菜单业务处理
 * @author Caesar Liu
 * @date 2021-03-30 10:51
 */
public interface SystemMenuBiz {

    /**
     * 菜单排序
     * @author Caesar Liu
     * @date 2021-03-30 21:48
     */
    void updateSort(UpdateSystemMenuSortDTO dto);

    /**
     * 查询菜单节点列表
     * @author Caesar Liu
     * @date 2021-03-29 20:24
     */
    List<SystemMenuNodeVO> findTree(Integer userId);

    /**
     * 分页查询菜单
     * @author Caesar Liu
     * @date 2021-03-29 20:19
     */
    List<SystemMenuListVO> findList();
}
