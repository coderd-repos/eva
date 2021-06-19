package com.eva.core.annotation.excel;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.lang.annotation.*;

/**
 * 标记为Excel列
 * @author Eva.Caesar Liu
 * @date 2021-06-18 23:19
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

    /**
     * 列名
     */
    String name();

    /**
     * 列宽（单位为字符），-1自动计算
     */
    int width() default -1;

    /**
     * 排序，值越小越靠前，-1按字段反射顺序排序
     */
    int index() default -1;

    /**
     * 对齐方式
     */
    HorizontalAlignment align() default HorizontalAlignment.LEFT;

    /**
     * 字体颜色
     */
    IndexedColors color () default IndexedColors.BLACK;

    /**
     * 粗体
     */
    boolean bold () default false;

    /**
     * 斜体
     */
    boolean italic () default false;

    /**
     * 值映射，如0=女;1=男
     */
    String valueMapping() default "";

    /**
     * 数据前缀
     */
    String prefix() default "";

    /**
     * 数据后缀
     */
    String suffix() default "";

    /**
     * 日期格式
     */
    String dateFormat() default "yyyy-MM-dd";

    /**
     * 自定义数据处理器
     */
    Class handler() default ExcelDataHandlerAdapter.class;

    /**
     * 自定义数据处理器参数
     */
    String[] args() default {};

}
