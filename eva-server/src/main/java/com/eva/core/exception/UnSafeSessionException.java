package com.eva.core.exception;

/**
 * 不安全的会话异常
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
public class UnSafeSessionException extends RuntimeException {

    public UnSafeSessionException () {
        super("不安全的会话");
    }
}
