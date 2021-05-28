package com.eva.core.authorizing;

import com.eva.core.exception.UnSafeSessionException;

/**
 * Token 管理器接口
 * @author Eva
 * @date 2021-05-28 11:08
 */
public interface TokenManager {

    /**
     * 生成token
     * @author Eva
     * @date 2021-05-28 11:09
     */
    String build();

    /**
     * 验证token
     * @author Eva
     * @date 2021-05-28 11:09
     */
    void check(String token) throws UnSafeSessionException;
}
