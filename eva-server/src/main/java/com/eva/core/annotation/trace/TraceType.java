package com.eva.core.annotation.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 跟踪类型
 * @author Caesar Liu
 * @date 2021-05-29 14:09
 */
@Getter
@AllArgsConstructor
public enum TraceType {
    AUTO("AUTO", "自动识别"),
    CREATE("CREATE", "新建"),
    UPDATE("UPDATE", "修改"),
    DELETE("DELETE", "删除"),
    DELETE_BATCH("DELETE_BATCH", "批量删除"),
    IMPORT("IMPORT", "导入"),
    EXPORT("EXPORT", "导出"),
    UNKNOWN("UNKNOWN", "未知操作"),
    ;

    private String type;

    private String remark;
}
