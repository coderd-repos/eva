package com.eva.core.annotation.excel;

/**
 * 标记为Excel Sheet
 * @author Eva.Caesar Liu
 * @date 2021-06-18 23:18
 */
public @interface ExcelSheet {

    /**
     * Sheet名称
     */
    String name();

    /**
     * Sheet排序，值越小越靠前
     */
    int index();
}
