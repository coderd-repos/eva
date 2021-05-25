package com.eva.core.annotation.duplicate;

import java.lang.annotation.*;

/**
 * 防重复提交注解
 * @author Caesar Liu
 * @date 2021-05-25 10:23
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicateSubmit {

    /**
     * 标记防重复提交的验证类
     * @author Caesar Liu
     * @date 2021-05-25 11:10
     */
    Class value() default DuplicateSubmitDefaultHandler.class;

    /**
     * 间隔时间，小于此时间视为重复提交
     * @author Caesar Liu
     * @date 2021-05-25 11:27
     */
    long interval() default 500;
}
