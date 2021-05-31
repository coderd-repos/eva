package com.eva.biz.system;

import com.eva.dao.system.model.SystemPermission;

import java.util.List;

/**
 * 系统权限业务处理
 * @author Eva.Caesar Liu
 * @date 2021-05-21 15:45
 */
public interface SystemPermissionBiz {

    /**
     * 删除
     * @author Eva.Caesar Liu
     * @date 2021-05-26 16:34
     */
    void deleteById(Integer id);

    /**
     * 批量删除
     * @author Eva.Caesar Liu
     * @date 2021-05-26 16:37
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 创建
     * @author Eva.Caesar Liu
     * @date 2021-05-21 15:45
     */
    Integer create(SystemPermission systemPermission);

    /**
     * 修改
     * @author Eva.Caesar Liu
     * @date 2021-05-21 15:48
     */
    void updateById(SystemPermission systemPermission);
}
