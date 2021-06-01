package com.eva.core.annotation.pr;

import com.eva.core.constants.ResponseStatus;

import java.lang.annotation.*;

/**
 * 防重复提交注解
 * @author Eva.Caesar Liu
 * @date 2021-05-25 10:23
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreventRepeat {

    /**
     * 防重复规则设定类
     */
    Class value() default PreventRepeatDefaultHandler.class;

    /**
     * 间隔时间(ms)，小于此时间视为重复提交
     */
    int interval() default 800;

    /**
     * 错误消息
     */
    String message() default "";

    /**
     * 1分钟内限制的请求次数（<=0时表示不限制）
     */
    int limit() default 0;

    /**
     * 被限制时的错误消息
     */
    String limitMessage() default "";

    /**
     * 超出请求限制次数时锁定的时长(ms)
     */
    int lockTime() default 600000;

}
