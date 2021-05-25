package com.eva.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    // 400开头表示参数错误
    BAD_REQUEST(4000, "参数错误"),
    DATA_EMPTY(4001, "找不到目标数据"),
    DATA_EXISTS(4002, "记录已存在"),
    PWD_INCORRECT(4003, "密码不正确"),
    VERIFICATION_CODE_INCORRECT(4004, "验证码不正确"),
    DUPLICATE_SUBMIT(4005, "重复提交"),
    ;

    private Integer code;

    private String message;
}
