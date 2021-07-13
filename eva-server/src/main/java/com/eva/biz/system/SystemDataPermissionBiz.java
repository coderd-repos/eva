package com.eva.biz.system;

import com.eva.dao.system.model.SystemDataPermission;

/**
 * 系统数据权限业务处理
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
public interface SystemDataPermissionBiz {

    /**
     * 创建
     * @param systemDataPermission 数据权限对象
     *
     * @return Integer
     */
    Integer create(SystemDataPermission systemDataPermission);

    /**
     * 修改
     * @param systemDataPermission 数据权限对象
     */
    void update(SystemDataPermission systemDataPermission);

    /**
     * 修改状态
     * @param systemDataPermission 数据权限对象
     */
    void updateStatus (SystemDataPermission systemDataPermission);
}
