package com.eva.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常等级
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Getter
@AllArgsConstructor
public enum ExceptionLevel {
    DANGER((byte)10, "高"),
    WARN((byte)5, "中"),
    LOW((byte)0, "低"),
    ;

    private byte level;

    private String remark;
}
