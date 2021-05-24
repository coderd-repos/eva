package com.eva.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    DATA_EMPTY(4001, "找不到目标数据"),
    DATA_EXISTS(4002, "记录已存在"),
    PWD_INCORRECT(4003, "密码不正确"),
    VERIFICATION_CODE_INCORRECT(4004, "验证码不正确"),
    ;

    private Integer code;

    private String message;
}
