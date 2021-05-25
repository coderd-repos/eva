package com.eva.config.shiro;

import com.eva.core.model.ApiResponse;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Shiro认证过滤器，处理未认证情况的响应
 * @author Eva
 * @date 2021-03-28 10:03
 */
public class ShiroAuthFilter extends FormAuthenticationFilter {

    public ShiroAuthFilter() {
        super();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        servletResponse.setHeader("content-type", "application/json;charset=UTF-8");
        servletResponse.getWriter().write(JSON.toJSONString(ApiResponse.failed(HttpStatus.UNAUTHORIZED.value(), "未登录或登录信息已过期")));
        return Boolean.FALSE;
    }
}
