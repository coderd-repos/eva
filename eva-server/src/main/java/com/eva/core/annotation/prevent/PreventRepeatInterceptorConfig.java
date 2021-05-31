package com.eva.core.annotation.prevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Swagger拦截器配置
 * @author Eva.Caesar Liu
 * @date 2021/05/15 18:44
 */
@Configuration
public class PreventRepeatInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private PreventRepeatInterceptor duplicateSubmitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(duplicateSubmitInterceptor).addPathPatterns("/**");
    }
}