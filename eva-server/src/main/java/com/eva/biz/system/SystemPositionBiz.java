package com.eva.biz.system;


import com.eva.dao.system.model.SystemPosition;
import com.eva.dao.system.vo.SystemPositionListVO;

import java.util.List;

public interface SystemPositionBiz {

    /**
     * 新建
     * @author Eva
     * @date 2021-05-22 14:14
     */
    Integer create(SystemPosition systemPosition);

    /**
     * 编辑
     * @author Eva
     * @date 2021-05-22 14:14
     */
    void updateById(SystemPosition systemPosition);

    /**
     * 查询岗位列表树
     * @author Eva
     * @date 2021-05-16 15:29
     */
    List<SystemPositionListVO> findTree();

    /**
     * 删除
     * @author Eva
     * @date 2021-05-24 22:00
     */
    void deleteById(Integer id);

    /**
     * 批量删除
     * @author Eva
     * @date 2021-05-24 22:00
     */
    void deleteByIdInBatch(List<Integer> ids);
}
