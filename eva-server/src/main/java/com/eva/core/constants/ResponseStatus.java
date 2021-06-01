package com.eva.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态定义
 * @author Eva.Caesar Liu
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
    VERIFICATION_CODE_INCORRECT(4004, "验证码不正确或已过期"),
    ACCOUNT_INCORRECT(4005, "账号或密码不正确"),
    // 500开头表示未知的服务异常
    SERVER_ERROR(5000, "系统繁忙，请联系系统管理员"),
    // 510开头表示可能导致数据错误的异常
    REPEAT_REQUEST(5100, "请勿重复提交"),
    MASSIVE_REQUEST(5101, "请求过于频繁"),
    NOT_ALLOWED(5110, "不允许的操作"),
    ;

    private int code;

    private String message;
}
