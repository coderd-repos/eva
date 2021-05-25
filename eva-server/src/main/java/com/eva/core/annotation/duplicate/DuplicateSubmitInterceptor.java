package com.eva.core.annotation.duplicate;

import com.alibaba.fastjson.JSON;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.ResponseStatus;
import com.eva.service.proxy.CacheProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 防重复提交处理
 * @author Caesar Liu
 * @date 2021-05-25 10:43
 */
@Component
public class DuplicateSubmitInterceptor extends HandlerInterceptorAdapter {

    private static ApplicationContext applicationContext;

    @Autowired
    private CacheProxy cacheProxy;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            DuplicateSubmit duplicateSubmit = method.getAnnotation(DuplicateSubmit.class);
            // 接口未添加防重复提交注解
            if (duplicateSubmit == null) {
                return Boolean.TRUE;
            }
            // 获取验证方法
            Object handlerInstance = applicationContext.getBean(duplicateSubmit.value());
            Method isDuplicateMethod = duplicateSubmit.value().getMethod(DuplicateSubmitAdapter.METHOD_IS_DUPLICATE, HttpServletRequest.class, Long.class);
            // 执行验证
            if((Boolean)isDuplicateMethod.invoke(handlerInstance, request, duplicateSubmit.interval())) {
                response.setHeader("content-type", "application/json;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(ApiResponse.failed(ResponseStatus.DUPLICATE_SUBMIT)));
                return Boolean.FALSE;
            }
            // 写入requestKey
            Method signMethod = duplicateSubmit.value().getMethod(DuplicateSubmitAdapter.METHOD_REQUEST_KEY, HttpServletRequest.class);
            cacheProxy.set((String)signMethod.invoke(handlerInstance, request), System.currentTimeMillis());
            return Boolean.TRUE;
        }
        return super.preHandle(request, response, handler);
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (DuplicateSubmitInterceptor.applicationContext == null) {
            DuplicateSubmitInterceptor.applicationContext = applicationContext;
        }
    }
}
