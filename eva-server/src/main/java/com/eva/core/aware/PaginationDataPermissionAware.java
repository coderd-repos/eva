package com.eva.core.aware;

import com.eva.core.model.PageData;
import com.eva.core.model.PageWrap;

/**
 * 带分页的数据权限意识
 * @author Eva.Caesar Liu
 * @date 2021-06-11 19:52
 */
public interface PaginationDataPermissionAware<T, Q> {

    /**
     * 全部数据权限
     *
     * @return List<T>
     */
    PageData<T> all(PageWrap<Q> pageWrap);
}
