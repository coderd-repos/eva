package com.eva.core.annotation.prevent;

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
    long interval() default 800;

    /**
     * 错误消息
     */
    String message() default "请勿重复提交";

    /**
     * 1分钟内限制请求次数（<=0时表示不限制）
     */
    int limit() default 0;

    /**
     * 超出限制锁定时长(ms)
     */
    int lockTime() default 600000;

}
