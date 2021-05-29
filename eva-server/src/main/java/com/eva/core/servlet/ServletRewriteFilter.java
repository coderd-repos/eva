package com.eva.config.filter;

import com.eva.core.servlet.ServletDuplicateRequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 全局过滤器，处理请求流传递，防止请求流被拦截器进行一次读取后关闭导致流无法连续读取。
 * 技术参考：https://blog.csdn.net/AlbenXie/article/details/114868245
 * @author Eva
 * @date 2021-05-25 16:23
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "globalFilter")
public class GlobalFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        ServletRequest requestWrapper = new ServletDuplicateRequestWrapper(httpServletRequest);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}
