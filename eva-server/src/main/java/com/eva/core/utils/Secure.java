package com.eva.core.utils;

import org.springframework.util.DigestUtils;

/**
 * 安全处理工具类
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
public class Secure {

    /**
     * 加密密码
     * @param password 密码
     * @param salt 密码盐
     *
     * @return String
     */
    public String encryptPassword(String password, String salt) {
        return DigestUtils.md5DigestAsHex((password + salt).getBytes());
    }
}
