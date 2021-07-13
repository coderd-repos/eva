package com.eva.core.annotation.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 跟踪类型
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
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
    RESET("RESET", "重置"),
    UNKNOWN("UNKNOWN", "未知操作"),
    ;

    /**
     * 跟踪类型
     */
    private String type;

    /**
     * 跟踪备注
     */
    private String remark;
}
