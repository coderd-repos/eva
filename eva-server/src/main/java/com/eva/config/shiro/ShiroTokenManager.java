package com.eva.config.shiro;

import com.eva.core.authorizing.TokenManager;
import com.eva.core.exception.UnSafeSessionException;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 默认Token管理器
 * @author Eva
 * @date 2021-05-28 11:14
 */
@Component
public class ShiroDefaultTokenManager implements TokenManager {

    @Override
    public String build() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void check(String token) throws UnSafeSessionException {
        if (token == null || token.length() != 36) {
            throw new UnSafeSessionException();
        }
    }
}
