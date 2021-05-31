package com.eva.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局拦截器配置
 * @author Eva.Caesar Liu
 * @date 2021/03/26 19:48
 */
@Configuration
public class GlobalInterceptorConfig implements WebMvcConfigurer {

    @Value("${project.mode:}")
    private String mode;

    @Autowired
    private DemoInterceptor demoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (DemoInterceptor.ENABLE_MODE.equals(mode)) {
            registry.addInterceptor(demoInterceptor).addPathPatterns("/**").excludePathPatterns("/swagger-ui.html", "/doc.html");
        }
    }
}