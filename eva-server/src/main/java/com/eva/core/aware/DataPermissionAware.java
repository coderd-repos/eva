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

    /**
     * 自定义数据权限
     * @param customData 自定义数据，多条数据使用","隔开
     *
     * @return List<T>
     */
    List<T> custom (String customData);

    /**
     * 用户所属及其子孙节点
     * @param userId 用户ID
     *
     * @return List<T>
     */
    List<T> userChildren (Integer userId);

    /**
     * 用户所属及其子节点
     * @param userId 用户ID
     *
     * @return List<T>
     */
    List<T> userChild (Integer userId);

    /**
     * 仅用户所属
     * @param userId 用户ID
     *
     * @return List<T>
     */
    List<T> user (Integer userId);
}
