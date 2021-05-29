package com.eva.core.annotation.trace;

import java.lang.annotation.*;

/**
 * 跟踪日志注解
 * @author Eva
 * @date 2021-05-29 10:24
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Trace {

    /**
     * 模块名称
     */
    String module() default "";

    /**
     * 操作类型
     */
    TraceType type() default TraceType.AUTO;

    /**
     * 备注，如果为空，则从操作类型中读取备注
     */
    String remark() default "";

    /**
     * 是否解除
     */
    boolean exclude() default false;

    /**
     * 是否记录请求参数
     */
    boolean withRequestParameters() default true;

    /**
     * 是否记录请求结果
     */
    boolean withRequestResult () default true;
}
