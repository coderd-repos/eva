package com.eva.core.annotation.excel;

/**
 * Excel数据格式处理适配器
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
public interface ExcelDataHandlerAdapter {

    /**
     * 格式化
     * @param args 参数集合，第一个参数为单元格数据
     *
     * @return String
     */
    Object format (Object... args);
}
