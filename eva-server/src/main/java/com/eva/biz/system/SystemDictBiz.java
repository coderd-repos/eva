package com.eva.biz.system;

import com.eva.dao.system.model.SystemDict;

/**
 * 字典业务处理
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
public interface SystemDictBiz {

    /**
     * 创建
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    Integer create(SystemDict systemDict);

    /**
     * 编辑
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    void updateById(SystemDict systemDict);

}
