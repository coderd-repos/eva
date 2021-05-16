package com.yiwa.interceptor;

import com.yiwa.core.model.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
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

    private static final String CREATE_USER = "createUser";

    private static final String UPDATE_TIME = "updateTime";

    private static final String UPDATE_USER = "updateUser";

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
        // 查询语句
        if (SqlCommandType.SELECT == sqlCommandType) {
            this.handleQueryStatement(target);
        }
        // 创建语句
        if (SqlCommandType.INSERT == sqlCommandType) {
            this.handleOperaStatement(target, CREATE_TIME, CREATE_USER);
        }
        // 更新语句
        else if (SqlCommandType.UPDATE == sqlCommandType) {
            this.handleOperaStatement(target, UPDATE_TIME, UPDATE_USER);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    /**
     * 处理新增和编辑语句
     * @author Caesar Liu
     * @date 2019/3/2 11:52
     */
    private void handleOperaStatement(Object target, String... fieldNames) throws Exception{
        // 操作时间
        Field operaTimeField = target.getClass().getDeclaredField(fieldNames[0]);
        Object operaTime = this.getFieldValue(operaTimeField, target);
        if(operaTime == null) {
            this.setFieldValue(operaTimeField, target, new Date());
        }
        // 操作人
        Field operaUserField = target.getClass().getDeclaredField(fieldNames[1]);
        Object operaUser = this.getFieldValue(operaUserField, target);
        if(operaUser == null) {
            this.setFieldValue(operaUserField, target, this.getLoginUser().getId());
        }
    }

    /**
     * 处理查询语句
     * @author Caesar Liu
     * @date 2019/3/2 11:52
     */
    private void handleQueryStatement(Object target) throws Exception{
        Field field = target.getClass().getDeclaredField(DELETED);
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

    /**
     * 获取登录用户信息
     * @author Caesar Liu
     * @date 2021-05-16 16:22
     */
    private LoginUserInfo getLoginUser () {
        return (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
    }
}
