package com.yiwa.biz.system;


import com.yiwa.dao.system.vo.SystemPositionListVO;

import java.util.List;

public interface SystemPositionBiz {

    /**
     * 查询部门
     * @author Yiwa
     * @date 2021-05-16 15:29
     */
    List<SystemPositionListVO> findList();
}
