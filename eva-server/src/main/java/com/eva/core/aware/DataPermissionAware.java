package com.eva.core.aware;

import java.util.List;

/**
 * 数据权限意识
 * @author Eva.Caesar Liu
 * @date 2021-06-11 19:52
 */
public interface DataPermissionAware<T> {

    /**
     * 全部数据权限
     *
     * @return List<T>
     */
    List<T> all ();
}
