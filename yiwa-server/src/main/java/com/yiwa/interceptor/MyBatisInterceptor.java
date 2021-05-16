package com.yiwa.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * MyBatis 拦截器
 * - INSERT语句默认填充创建时间字段
 * - UPDATE语句默认填充更新时间字段
 * - SELECT语句默认填充DELETED字段
 * @author Caesar Liu
 * @date 2019/3/2 11:00
 */
@Slf4j
@Component
@Intercepts({
        @Signature(type= Executor.class, method = "update", args={MappedStatement.class, Object.class}),
        @Signature(type= Executor.class, method = "query", args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MyBatisInterceptor implements Interceptor {

    private static final String CREATE_TIME = "createTime";

    private static final String UPDATE_TIME = "updateTime";

    private static final String DELETED = "deleted";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object target = invocation.getArgs()[1];
        if(target instanceof MapperMethod.ParamMap) {
            try {
                // 可能不包含record
                target = ((MapperMethod.ParamMap) target).get("param1");
            } catch (Exception e) {
            }
        }
        if (target == null)
            return invocation.proceed();
        Date now = new Date();
        // 查询语句
        if (SqlCommandType.SELECT == sqlCommandType) {
            Field field = target.getClass().getDeclaredField(DELETED);
            this.initDeleted(field, target);
        }
        // 创建语句
        if (SqlCommandType.INSERT == sqlCommandType) {
            Field field = target.getClass().getDeclaredField(CREATE_TIME);
            this.initCreateTime(field, target, now);

        }
        // 更新语句
        else if (SqlCommandType.UPDATE == sqlCommandType) {
            Field field = target.getClass().getDeclaredField(UPDATE_TIME);
            this.initUpdateTime(field, target, now);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    /**
     * 初始化创建时间
     * @author Caesar Liu
     * @date 2019/3/2 11:52
     */
    private void initCreateTime(Field field, Object target, Date date) throws Exception{
        Object value = this.getFieldValue(field, target);
        if(value == null) {
            this.setFieldValue(field, target, date);
        }
    }

    /**
     * 初始化更新时间
     * @author Caesar Liu
     * @date 2019/3/2 11:52
     */
    private void initUpdateTime(Field field, Object target, Date date) throws Exception{
        Object value = this.getFieldValue(field, target);
        if(value == null) {
            this.setFieldValue(field, target, date);
        }
    }

    /**
     * 初始化删除字段条件
     * @author Caesar Liu
     * @date 2019/3/2 11:52
     */
    private void initDeleted(Field field, Object target) throws Exception{
        Object value = this.getFieldValue(field, target);
        if(value == null) {
            this.setFieldValue(field, target, Boolean.FALSE);
        }
    }

    /**
     * 给属性赋值
     * @author Caesar Liu
     * @date 2019/3/2 11:36
     */
    private void setFieldValue(Field field, Object target, Object value) throws Exception {
        field.setAccessible(true);
        field.set(target, value);
        field.setAccessible(false);
    }

    /**
     * 获取属性值
     * @author Caesar Liu
     * @date 2019/3/2 11:40
     */
    private Object getFieldValue(Field field, Object target) throws Exception {
        field.setAccessible(true);
        Object value = field.get(target);
        field.setAccessible(false);
        return value;
    }
}
