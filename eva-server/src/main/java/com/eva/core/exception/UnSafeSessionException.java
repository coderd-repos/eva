package com.eva.core.exception;

/**
 * 不安全的会话异常
 * @author Caesar Liu
 * @date 2021-05-28 11:46
 */
public class UnSafeSessionException extends RuntimeException {

    public UnSafeSessionException () {
        super("不安全的会话");
    }
}
