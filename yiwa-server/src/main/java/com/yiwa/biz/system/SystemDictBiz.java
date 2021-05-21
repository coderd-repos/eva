package com.yiwa.biz.system;

import com.yiwa.dao.system.model.SystemDict;

/**
 * 字典业务处理
 * @author Yiwa
 * @date 2021-05-21 15:59
 */
public interface SystemDictBiz {

    /**
     * 创建
     * @author Yiwa
     * @date 2021-05-21 15:59
     */
    Integer create(SystemDict systemDict);

    /**
     * 编辑
     * @author Yiwa
     * @date 2021-05-21 15:59
     */
    void updateById(SystemDict systemDict);

}
