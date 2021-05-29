package com.eva.core.annotation.trace;

import com.eva.core.model.LoginUserInfo;
import com.eva.core.servlet.ContainBodyRequestWrapper;
import com.eva.core.utils.RequestHeaderUtil;
import com.eva.dao.system.model.SystemTraceLog;
import com.eva.service.system.SystemTraceLogService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 跟踪日志处理
 * @author Eva
 * @date 2021-05-29 10:22
 */
@Slf4j
@Component
public class TraceInterceptor extends HandlerInterceptorAdapter {

    @Value("${project.version}")
    private String serviceVersion;

    @Autowired
    private SystemTraceLogService systemTraceLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            if (!this.allowTrace(handler)) {
                return Boolean.TRUE;
            }
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Trace trace = method.getAnnotation(Trace.class);
            // 获取跟踪模块
            String module = "";
            if (trace != null && StringUtils.isNotBlank(trace.module())) {
                module = trace.module();
            }
            if ("".equals(module)) {
                Api api = method.getDeclaringClass().getAnnotation(Api.class);
                if (api != null) {
                    module = StringUtils.join(api.tags(), ", ");
                }
            }
            // 获取跟踪类型
            TraceType traceType = trace == null ? null : trace.type();
            if (traceType == null) {
                traceType = this.smartType(request);
            }
            SystemTraceLog log = new SystemTraceLog();
            // 用户信息
            LoginUserInfo userInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
            if (userInfo != null) {
                log.setUserId(userInfo.getId());
                log.setUsername(userInfo.getUsername());
                log.setUserRealname(userInfo.getRealname());
                log.setUserRoles(StringUtils.join(userInfo.getRoles(), ","));
                log.setUserPermissions(StringUtils.join(userInfo.getPermissions(), ","));
            }
            // 操作信息
            log.setOperaModule(module);
            log.setOperaType(traceType.getType());
            log.setOperaRemark(traceType.getRemark());
            if (trace != null) {
                log.setOperaRemark("".equals(trace.remark()) ? traceType.getRemark() : trace.remark());
            }
            log.setOperaTime(new Date());
            // 请求信息
            log.setRequestUri(request.getRequestURI());
            log.setRequestMethod(request.getMethod());
            if (trace == null || trace.withRequestParameters()) {
                if (HttpMethod.POST.matches(request.getMethod())) {
                    ContainBodyRequestWrapper requestWrapper = new ContainBodyRequestWrapper(request);
                    log.setRequestParams(requestWrapper.getBody());
                } else {
                    log.setRequestParams(request.getQueryString());
                }
            }
            // 辅助信息
            log.setIp(RequestHeaderUtil.getRequestIp(request));
            log.setServiceVersion(serviceVersion);
            log.setPlatform(request.getHeader("x-platform") == null ? "PC" : request.getHeader("x-platform"));
            log.setClientInfo(RequestHeaderUtil.getClientInfo(request));
            log.setSystemInfo(RequestHeaderUtil.getSystemInfo(request));
            systemTraceLogService.asyncCreate(log);
            request.setAttribute("eva-trace-id", log.getId());
        } catch (Exception e) {
            log.warn("Eva Trace throw an exception, you can get detail message by debug mode.");
            log.debug(e.getMessage(), e);
        }
        return Boolean.TRUE;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (!this.allowTrace(handler)) {
            return;
        }
        // 获取跟踪ID
        Object traceId = request.getAttribute("eva-trace-id");
        if (traceId == null) {
            return;
        }
        SystemTraceLog log = new SystemTraceLog();
        log.setId(Integer.valueOf(traceId.toString()));
        log.setStatus(TraceStatus.SUCCESS.getCode());
        if (ex != null) {
            log.setStatus(TraceStatus.FAILED.getCode());
            log.setExceptionStack(ex.getMessage());
        }
        // 记录响应内容
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Trace trace = method.getAnnotation(Trace.class);
        if (trace == null || trace.withRequestResult()) {
            // log.setRequestResult();
        }
        systemTraceLogService.updateById(log);
    }

    /**
     * 是否允许跟踪
     */
    private Boolean allowTrace (Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Trace trace = method.getAnnotation(Trace.class);
        // 方法排除
        if (trace != null && trace.exclude()) {
            return Boolean.FALSE;
        }
        // 类排除
        trace = method.getDeclaringClass().getAnnotation(Trace.class);
        if (trace != null && trace.exclude()) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 获取跟踪类型
     */
    private TraceType smartType (HttpServletRequest request) {
        // 批量删除
        if (request.getRequestURI().matches(".+/delete/batch$")) {
            return TraceType.DELETE_BATCH;
        }
        // 删除
        if (request.getRequestURI().matches(".+/delete.*")) {
            return TraceType.DELETE;
        }
        // 新增
        if (request.getRequestURI().matches(".+/create.*")) {
            return TraceType.CREATE;
        }
        // 修改
        if (request.getRequestURI().matches(".+/update.*")) {
            return TraceType.UPDATE;
        }
        // 导入
        if (request.getRequestURI().matches(".+/import.*")) {
            return TraceType.IMPORT;
        }
        // 导出
        if (request.getRequestURI().matches(".+/export.*")) {
            return TraceType.EXPORT;
        }
        return TraceType.UNKNOWN;
    }
}
