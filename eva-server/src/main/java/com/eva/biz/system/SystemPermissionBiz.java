package com.eva.biz.system;

import com.eva.dao.system.model.SystemPermission;

import java.util.List;

/**
 * 系统权限业务处理
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
public interface SystemPermissionBiz {

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

    /**
     * 创建
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    Integer create(SystemPermission systemPermission);

    /**
     * 修改
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    void updateById(SystemPermission systemPermission);
}
