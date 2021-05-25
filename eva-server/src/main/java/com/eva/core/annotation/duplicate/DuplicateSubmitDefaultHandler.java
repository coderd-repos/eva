package com.eva.core.annotation.duplicate;

import com.eva.core.servlet.ContainBodyRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认防重复提交实现
 * @author Eva
 * @date 2021-05-25 11:01
 */
@Slf4j
@Component
public class DuplicateSubmitDefaultHandler extends DuplicateSubmitAdapter {

    private static final String REQUEST_URI = "REQUEST_URI";

    private static final String REQUEST_BODY_PARAMETERS = "REQUEST_BODY_PARAMETERS";

    private static final String COOKIES = "COOKIES";

    // 将请求Key存储在线程对象中
    private ThreadLocal<String> requestKeyThreadLocal = new ThreadLocal<>();

    @Override
    public String sign(HttpServletRequest request) {
        String requestKey = requestKeyThreadLocal.get();
        if (requestKey != null) {
            requestKeyThreadLocal.set(null);
            return requestKey;
        }
        // 获取参数
        Map<String, Object> parameters = this.getParameters(request);
        // 构建参数签名字符串
        StringBuilder signString = new StringBuilder();
        for(String key : parameters.keySet()) {
            signString.append(key).append("=").append(parameters.get(key)).append(";");
        }
        // 参数签名
        requestKey = DigestUtils.md5DigestAsHex(signString.toString().getBytes());
        requestKeyThreadLocal.set(requestKey);
        return requestKey;
    }

    /**
     * 获取参数
     * @author Eva
     * @date 2021-05-25 14:18
     */
    private Map<String, Object> getParameters(HttpServletRequest request) {
        HashMap<String, Object> paramMap = new HashMap<>();
        // 获取请求路径
        paramMap.put(REQUEST_URI, request.getRequestURI());
        // 获取请求体参数
        String bodyParameters = new ContainBodyRequestWrapper(request).getBody();
        paramMap.put(REQUEST_BODY_PARAMETERS, bodyParameters);
        // 获取Cookie信息
        Cookie[] cookies = request.getCookies();
        StringBuilder cookieString = new StringBuilder();
        for (Cookie cookie : cookies) {
            cookieString.append(cookie.getName()).append(cookie.getValue()).append(";");
        }
        paramMap.put(COOKIES, cookieString);
        return paramMap;
    }
}
