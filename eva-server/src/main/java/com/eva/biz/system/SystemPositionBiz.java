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
     * 查询部门
     * @author Eva
     * @date 2021-05-16 15:29
     */
    List<SystemPositionListVO> findList();
}
