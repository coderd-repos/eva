package com.eva.core.aware;

import com.eva.core.constants.DataPermissionConstants;

import java.util.List;

/**
 * 数据权限意识
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
public interface DataPermissionAware<T> {

    /**
     * 默认数据（当用户未配置相关数据权限时获取该数据）
     *
     * @param userId 用户ID
     * @return List<T>
     */
    List<T> defaultData(Integer userId);

    /**
     * 获取模块对象
     *
     * @return DataPermissionConstants.Module
     */
    DataPermissionConstants.Module module();
}
