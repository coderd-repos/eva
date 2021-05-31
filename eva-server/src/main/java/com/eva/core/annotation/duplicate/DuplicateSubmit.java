package com.eva.core.annotation.duplicate;

import java.lang.annotation.*;

/**
 * 防重复提交注解
 * @author Eva
 * @date 2021-05-25 10:23
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicateSubmit {

    /**
     * 防重复规则设定类
     */
    Class value() default DuplicateSubmitDefaultHandler.class;

    /**
     * 间隔时间(ms)，小于此时间视为重复提交
     */
    long interval() default 800;

    /**
     * 错误消息
     */
    String message() default "请勿重复提交";

    /**
     * 在指定时间的时间内限制请求次数
     */
    int limit() default 0;

}
