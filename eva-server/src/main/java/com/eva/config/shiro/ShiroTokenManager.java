package com.eva.config.shiro;

import com.eva.core.exception.UnSafeSessionException;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 默认Token管理器
 * @author Eva.Caesar Liu
 * @date 2021-05-28 11:14
 */
@Component
public class ShiroTokenManager {

    String build() {
        return UUID.randomUUID().toString();
    }

    void check(String token) throws UnSafeSessionException {
        if (token == null || token.length() != 36) {
            throw new UnSafeSessionException();
        }
    }
}
