package com.eva.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.eva.core.model.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 演示模式拦截器
 * @author Eva
 * @date 2021-05-31 11:39
 */
@Component
public class DemoInterceptor implements HandlerInterceptor {

    // 启用值
    static final String ENABLE_MODE = "demo";

    // 放行的地址
    private List<String> publicUris = new ArrayList(){{
        this.add("/system/login");
        this.add("/system/logout");
        this.add("/system/getUserInfo");
        this.add("/common/captcha");
        this.add("/system/menu/treeNodes");
        this.add("/system/menu/treeList");
        this.add("/system/permission/page");
        this.add("/system/permission/all");
        this.add("/system/role/page");
        this.add("/system/role/all");
        this.add("/system/user/page");
        this.add("/system/department/page");
        this.add("/system/department/tree");
        this.add("/system/department/users");
        this.add("/system/position/page");
        this.add("/system/position/tree");
        this.add("/system/position/users");
        this.add("/system/traceLog/page");
        this.add("/system/loginLog/page");
        this.add("/system/dict/page");
        this.add("/system/dictData/page");
        this.add("/system/monitor/getOS");
    }};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (publicUris.contains(request.getRequestURI())) {
            return Boolean.TRUE;
        }
        response.setHeader("content-type", "application/json;charset=UTF-8");
        try {
            response.getWriter().write(JSON.toJSONString(ApiResponse.failed("演示模式下此功能被禁用")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }
}