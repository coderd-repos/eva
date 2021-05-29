package com.eva.core.annotation.trace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跟踪拦截器配置
 * @author Eva
 * @date 2021-05-29 10:24
 */
@Configuration
public class TraceInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private TraceInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
    }
}