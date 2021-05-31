package com.eva.core.annotation.pr;

import com.eva.core.servlet.ServletDuplicateInputStream;
import com.eva.core.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认防重复提交实现
 * @author Eva.Caesar Liu
 * @date 2021-05-25 11:01
 */
@Slf4j
@Component
public class PreventRepeatDefaultHandler extends PreventRepeatAdapter {

    @Override
    public String sign(HttpServletRequest request) {
        // 获取参数
        Map<String, Object> parameters = this.getParameters(request);
        // 构建参数签名字符串
        StringBuilder signString = new StringBuilder();
        for(String key : parameters.keySet()) {
            signString.append(key).append("=").append(parameters.get(key)).append(";");
        }
        // 参数签名
        return DigestUtils.md5DigestAsHex(signString.toString().getBytes());
    }

    /**
     * 获取参数
     * @author Eva.Caesar Liu
     * @date 2021-05-25 14:18
     */
    private Map<String, Object> getParameters(HttpServletRequest request) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("REQUEST_URI", request.getRequestURI());
        paramMap.put("USER_TOKEN", String.valueOf(request.getHeader("eva-auth-token")));
        paramMap.put("IP", Utils.User_Client.getIP(request));
        return paramMap;
    }
}
