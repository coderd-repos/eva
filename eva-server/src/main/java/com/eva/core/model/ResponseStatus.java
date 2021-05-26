package com.eva.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态定义
 * @author Eva
 * @date 2021-05-27 01:33
 */
@Getter
@AllArgsConstructor
public enum ResponseStatus {
    // 400开头表示参数错误
    BAD_REQUEST(4000, "参数错误"),
    DATA_EMPTY(4001, "找不到目标数据"),
    DATA_EXISTS(4002, "记录已存在"),
    PWD_INCORRECT(4003, "密码不正确"),
    VERIFICATION_CODE_INCORRECT(4004, "验证码不正确"),
    // 510开头表示可能导致数据错误的异常
    DUPLICATE_SUBMIT(5100, "请勿重复提交"),
    NOT_ALLOWED(5101, "不允许的操作"),
    ;

    private Integer code;

    private String message;
}
