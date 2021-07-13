package com.eva.core.annotation.pr;

import com.alibaba.fastjson.JSON;
import com.eva.core.model.ApiResponse;
import com.eva.core.constants.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
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
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Slf4j
@Component
public class PreventRepeatInterceptor extends HandlerInterceptorAdapter {

    private static ApplicationContext applicationContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return Boolean.TRUE;
        }
        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            PreventRepeat prAnnotation = method.getAnnotation(PreventRepeat.class);
            // 接口未添加防重复提交注解
            if (prAnnotation == null) {
                return Boolean.TRUE;
            }
            // 获取验证对象和方法
            PreventRepeatAdapter adapter = (PreventRepeatAdapter)applicationContext.getBean(prAnnotation.value());
            // 验证暴力请求
            if(prAnnotation.limit() > 0 && prAnnotation.lockTime() > 0 && adapter.massive(request, prAnnotation.limit(), prAnnotation.lockTime())) {
                log.warn("Eva Intercept a massive request，url：{}", request.getRequestURI());
                response.setHeader("content-type", "application/json;charset=UTF-8");
                ApiResponse apiResponse = ApiResponse.failed(ResponseStatus.MASSIVE_REQUEST);
                if (!"".equals(prAnnotation.message())) {
                    apiResponse.setMessage(prAnnotation.message());
                }
                response.getWriter().write(JSON.toJSONString(apiResponse));
                return Boolean.FALSE;
            }
            // 验证重复请求
            if(prAnnotation.interval() > 0 && adapter.prevent(request, prAnnotation.interval())) {
                log.warn("Eva Intercept a repeat request，url：{}", request.getRequestURI());
                response.setHeader("content-type", "application/json;charset=UTF-8");
                ApiResponse apiResponse = ApiResponse.failed(ResponseStatus.REPEAT_REQUEST);
                if (!"".equals(prAnnotation.limitMessage())) {
                    apiResponse.setMessage(prAnnotation.limitMessage());
                }
                response.getWriter().write(JSON.toJSONString(apiResponse));
                return Boolean.FALSE;
            }
        } catch (Exception e) {
            log.warn("Eva @PreventRepeat throw an exception, you can get detail message by debug mode");
            log.debug(e.getMessage(), e);
        }
        return Boolean.TRUE;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (PreventRepeatInterceptor.applicationContext == null) {
            PreventRepeatInterceptor.applicationContext = applicationContext;
        }
    }
}
