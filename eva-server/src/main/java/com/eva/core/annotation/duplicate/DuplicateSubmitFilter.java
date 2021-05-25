package com.eva.core.annotation.duplicate;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 防重复提交过滤器
 * 技术参考：https://blog.csdn.net/AlbenXie/article/details/114868245
 * @author Caesar Liu
 * @date 2021-05-25 16:23
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "duplicateSubmitFilter")
public class DuplicateSubmitFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        ServletRequest requestWrapper = new DuplicateSubmitRequestWrapper(httpServletRequest);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}
