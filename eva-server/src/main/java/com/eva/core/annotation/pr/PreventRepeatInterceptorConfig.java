package com.eva.core.annotation.pr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Swagger拦截器配置
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Configuration
public class PreventRepeatInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private PreventRepeatInterceptor preventRepeatInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(preventRepeatInterceptor).addPathPatterns("/**");
    }
}
