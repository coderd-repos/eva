package com.eva.core.annotation.trace;

import com.alibaba.fastjson.JSON;
import com.eva.core.model.ApiResponse;
import com.eva.core.model.LoginUserInfo;
import com.eva.core.servlet.ServletDuplicateInputStream;
import com.eva.core.servlet.ServletDuplicateOutputStream;
import com.eva.core.utils.RequestHeaderUtil;
import com.eva.core.utils.ServerUtil;
import com.eva.dao.system.model.SystemTraceLog;
import com.eva.service.system.SystemTraceLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.io.IOException;
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

    @Value("${trace.exclude-patterns:}")
    private String excludePatterns;

    @Value("${trace.smart}")
    private Boolean isSmart;

    private static final String ATTRIBUTE_TRACE_ID = "eva-trace-id";

    private static final String ATTRIBUTE_TRACE_TIME = "eva-trace-time";

    private static final int MAX_STORE_REQUEST_PARAM_SIZE = 1888;

    private static final int MAX_STORE_REQUEST_RESULT_SIZE = 1888;

    private static final int MAX_STORE_EXCEPTION_STACK_SIZE = 4888;

    private static final String MORE_DETAIL_STRING = "\n\n---------- more content is missing here ... ----------\n\n";

    @Autowired
    private SystemTraceLogService systemTraceLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            if (!this.allowTrace(request, handler)) {
                return Boolean.TRUE;
            }
            SystemTraceLog traceLog = new SystemTraceLog();
            Date now = new Date();
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Trace methodTrace = method.getAnnotation(Trace.class);
            Trace classTrace = method.getDeclaringClass().getAnnotation(Trace.class);
            // 获取跟踪类型
            TraceType traceType = methodTrace == null ? null : methodTrace.type();
            if (traceType == null) {
                traceType = this.smartType(request);
            }
            // 用户信息
            LoginUserInfo userInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
            if (userInfo != null) {
                traceLog.setUserId(userInfo.getId());
                traceLog.setUsername(userInfo.getUsername());
                traceLog.setUserRealname(userInfo.getRealname());
                traceLog.setUserRoles(StringUtils.join(userInfo.getRoles(), ","));
                traceLog.setUserPermissions(StringUtils.join(userInfo.getPermissions(), ","));
            }
            // 操作信息
            traceLog.setOperaModule(this.getModule(handler));
            traceLog.setOperaType(traceType.getType());
            traceLog.setOperaRemark(this.getOperaRemark(handler, traceType));
            traceLog.setOperaTime(now);
            // 请求信息
            traceLog.setRequestUri(request.getRequestURI());
            traceLog.setRequestMethod(request.getMethod());
            if (methodTrace == null || methodTrace.withRequestParameters() || (classTrace != null && classTrace.withRequestParameters())) {
                String requestParameters = request.getQueryString();
                if (HttpMethod.POST.matches(request.getMethod())) {
                    requestParameters = ((ServletDuplicateInputStream)request.getInputStream()).getBody();
                }
                traceLog.setRequestParams(requestParameters.length() > MAX_STORE_REQUEST_PARAM_SIZE ? requestParameters.substring(0, MAX_STORE_REQUEST_PARAM_SIZE) + MORE_DETAIL_STRING : requestParameters);
            }
            // 辅助信息
            traceLog.setServerIp(ServerUtil.getIpAddress());
            traceLog.setIp(RequestHeaderUtil.getRequestIp(request));
            traceLog.setServiceVersion(serviceVersion);
            traceLog.setPlatform(request.getHeader("x-platform") == null ? "PC" : request.getHeader("x-platform"));
            traceLog.setClientInfo(RequestHeaderUtil.getClientInfo(request));
            traceLog.setSystemInfo(RequestHeaderUtil.getSystemInfo(request));
            systemTraceLogService.create(traceLog);
            request.setAttribute(ATTRIBUTE_TRACE_ID, traceLog.getId());
            request.setAttribute(ATTRIBUTE_TRACE_TIME, now.getTime());
        } catch (Exception e) {
            log.warn("Eva Trace throw an exception, you can get detail message by debug mode.");
            log.debug(e.getMessage(), e);
        }
        return Boolean.TRUE;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws IOException {
        // 获取跟踪ID
        Object traceId = request.getAttribute(ATTRIBUTE_TRACE_ID);
        Object traceTime = request.getAttribute(ATTRIBUTE_TRACE_TIME);
        request.removeAttribute(ATTRIBUTE_TRACE_ID);
        request.removeAttribute(ATTRIBUTE_TRACE_TIME);
        if (traceId == null) {
            return;
        }
        // 计算操作耗时
        SystemTraceLog traceLog = new SystemTraceLog();
        traceLog.setId(Integer.valueOf(traceId.toString()));
        traceLog.setOperaSpendTime(Integer.valueOf("" + (System.currentTimeMillis() - Long.valueOf(traceTime.toString()))));
        // 记录响应内容
        String responseBody = ((ServletDuplicateOutputStream) response.getOutputStream()).getContent();
        ApiResponse apiResponse = null;
        try {
            apiResponse = JSON.parseObject(responseBody, ApiResponse.class);
        } catch (Exception e) {
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Trace methodTrace = method.getAnnotation(Trace.class);
        Trace classTrace = method.getDeclaringClass().getAnnotation(Trace.class);
        if (methodTrace == null || methodTrace.withRequestResult() || (classTrace != null && classTrace.withRequestResult())) {
            String requestResult = responseBody;
            if (apiResponse != null) {
                // 排除exception信息
                Throwable e = apiResponse.getException();
                apiResponse.setException(null);
                requestResult = JSON.toJSONString(apiResponse);
                apiResponse.setException(e);
            }
            traceLog.setRequestResult(requestResult.length() > MAX_STORE_REQUEST_RESULT_SIZE ? requestResult.substring(0, MAX_STORE_REQUEST_RESULT_SIZE) + MORE_DETAIL_STRING : requestResult);
        }
        // 请求成功
        if (ex == null && (apiResponse != null && apiResponse.isSuccess())) {
            traceLog.setStatus(TraceStatus.SUCCESS.getCode());
            systemTraceLogService.updateById(traceLog);
            return;
        }
        // 请求失败
        traceLog.setStatus(TraceStatus.FAILED.getCode());
        Throwable e = ex;
        if (e == null && apiResponse != null) {
            e = apiResponse.getException();
        }
        String error;
        if (e != null) {
            StackTraceElement[] trace = e.getStackTrace();
            StringBuilder exceptionStack = new StringBuilder(e + "\n");
            for (StackTraceElement traceElement : trace) {
                exceptionStack.append("  at ").append(traceElement).append("\n");
            }
            error = exceptionStack.toString();
        } else if (apiResponse != null){
            error = apiResponse.getMessage();
        } else {
            error = "Eva can not trace for action " + request.getRequestURI();
        }
        traceLog.setExceptionStack(error.length() > MAX_STORE_EXCEPTION_STACK_SIZE ? error.substring(0, MAX_STORE_EXCEPTION_STACK_SIZE) + MORE_DETAIL_STRING : error);
        systemTraceLogService.updateById(traceLog);
    }

    /**
     * 获取跟踪模块
     */
    private String getModule (Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Trace trace = method.getAnnotation(Trace.class);
        if (trace == null) {
            trace = method.getDeclaringClass().getAnnotation(Trace.class);
        }
        String module = "";
        if (trace != null && StringUtils.isNotBlank(trace.module())) {
            module = trace.module();
        }
        if (StringUtils.isBlank(module)) {
            Api api = method.getDeclaringClass().getAnnotation(Api.class);
            if (api != null) {
                module = StringUtils.join(api.tags(), ", ");
            }
        }
        return module;
    }

    /**
     * 获取操作备注
     */
    private String getOperaRemark (Object handler, TraceType traceType) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 从Trace注解中获取
        Trace trace = method.getAnnotation(Trace.class);
        if (trace != null && StringUtils.isNotBlank(trace.remark())) {
            return trace.remark();
        }
        // 从ApiOperation注解中获取
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        if (apiOperation != null && StringUtils.isNotBlank(apiOperation.value())) {
            return apiOperation.value();
        }
        return traceType.getRemark();
    }

    /**
     * 是否允许跟踪
     */
    private Boolean allowTrace (HttpServletRequest request, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Trace methodTrace = method.getAnnotation(Trace.class);
        Trace classTrace = method.getDeclaringClass().getAnnotation(Trace.class);
        // 非智能模式
        if (!isSmart) {
            if (methodTrace == null && classTrace == null) {
                return Boolean.FALSE;
            }
            if (methodTrace == null && classTrace.exclude()) {
                return Boolean.FALSE;
            }
        }
        // 方法排除
        if (methodTrace != null && methodTrace.exclude()) {
            return Boolean.FALSE;
        }
        // 类排除
        if (methodTrace == null && classTrace != null && classTrace.exclude()) {
            return Boolean.FALSE;
        }
        // 路径排除
        String[] patterns = excludePatterns.split(",");
        if (methodTrace == null && patterns.length > 0) {
            String uri = request.getRequestURI();
            for (String pattern : patterns) {
                if (uri.matches(pattern.trim())) {
                    return Boolean.FALSE;
                }
            }
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
        // 重置
        if (request.getRequestURI().matches(".+/reset.*")) {
            return TraceType.RESET;
        }
        return TraceType.UNKNOWN;
    }
}
