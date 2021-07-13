package com.eva.core.servlet;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 传递请求流/响应流副本
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "servletRewriteFilter")
public class ServletRewriteFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        // 防止请求流被拦截器进行一次读取后关闭导致流无法连续读取的问题
        ServletRequest requestWrapper = new ServletDuplicateRequestWrapper(httpServletRequest);
        // 允许响应流读取响应结果
        ServletResponse responseWrapper = new ServletDuplicateResponseWrapper(httpServletResponse);
        filterChain.doFilter(requestWrapper, responseWrapper);
    }
}
