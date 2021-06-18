package com.eva.core.annotation.excel;

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
     * 列宽
     */
    int width() default 20;

    /**
     * 排序，值越小越靠前
     */
    int index() default -1;

    /**
     * 对齐方式
     */
    Align align() default Align.LEFT;

    /**
     * 字体颜色
     */
    String fontColor () default "";

    /**
     * 字体大小
     */
    String fontSize () default "";

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
    Class<ExcelDataHandlerAdapter> handler() default ExcelDataHandlerAdapter.class;

    /**
     * 自定义数据处理器参数
     */
    String[] args() default {};

    /**
     * 对齐方式
     */
    enum Align {

        /**
         * 靠左
         */
        LEFT,

        /**
         * 居中
         */
        CENTER,

        /**
         * 靠右
         */
        RIGHT,
        ;
    }

}
