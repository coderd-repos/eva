package com.yiwa.biz.system;

import com.yiwa.dao.system.model.SystemDictData;

/**
 * 字典数据业务处理
 * @author Yiwa
 * @date 2021-05-21 22:18
 */
public interface SystemDictDataBiz {

    /**
     * 新建
     * @author Caesar Liu
     * @date 2021-05-21 22:19
     */
    Integer create(SystemDictData systemDictData);

    /**
     * 编辑
     * @author Caesar Liu
     * @date 2021-05-21 22:19
     */
    void updateById(SystemDictData systemDictData);
}
