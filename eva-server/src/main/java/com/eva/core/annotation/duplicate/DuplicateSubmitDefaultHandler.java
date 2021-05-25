package com.eva.core.annotation.duplicate;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认防重复提交实现
 * @author Caesar Liu
 * @date 2021-05-25 11:01
 */
@Component
public class DuplicateSubmitDefaultHandler extends DuplicateSubmitAdapter {

    private static final String REQUEST_URI = "REQUEST_URI";

    private static final String COOKIES = "COOKIES";

    @Override
    public String sign(HttpServletRequest request) {
        // 获取参数
        Map<String, Object> parameters = this.getParameters(request);
        // 构建参数签名字符串
        StringBuilder signString = new StringBuilder();
        for(String key : parameters.keySet()) {
            signString.append(key).append(parameters.get(key)).append(";");
        }
        // 参数签名
        return DigestUtils.md5DigestAsHex(signString.toString().getBytes());
    }

    /**
     * 获取参数
     * @author Caesar Liu
     * @date 2021-05-25 14:18
     */
    private Map<String, Object> getParameters(HttpServletRequest request) {
        HashMap<String, Object> paramMap = new HashMap<>();
        // 获取地址上的参数
        Enumeration<String> parameters = request.getParameterNames();
        while(parameters.hasMoreElements()) {
            String key = parameters.nextElement();
            paramMap.put(key, request.getParameter(key));
        }
        // 获取请求体参数
//        Enumeration<String> attributes = request.getAttributeNames();
//        while(attributes.hasMoreElements()) {
//            String key = attributes.nextElement();
//            paramMap.put(key, request.getAttribute(key));
//        }
        // 获取请求路径
        paramMap.put(REQUEST_URI, request.getRequestURI());
        // 获取cookie信息
        Cookie[] cookies = request.getCookies();
        StringBuilder cookieString = new StringBuilder();
        for (Cookie cookie : cookies) {
            cookieString.append(cookie.getName()).append(cookie.getValue()).append(";");
        }
        paramMap.put(COOKIES, cookieString);
        return paramMap;
    }
}
