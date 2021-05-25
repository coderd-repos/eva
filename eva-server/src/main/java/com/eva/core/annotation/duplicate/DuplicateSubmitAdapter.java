package com.eva.core.annotation.duplicate;

import com.eva.service.proxy.CacheProxy;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 防重复提交实现接口
 * @author Caesar Liu
 * @date 2021-05-25 10:58
 */
public abstract class DuplicateSubmitAdapter {

    // 验证是否重复方法名称
    public static final String METHOD_IS_DUPLICATE = "isDuplicate";

    // 获取请求Key方法名称
    public static final String METHOD_REQUEST_KEY = "sign";

    @Autowired
    private CacheProxy cacheProxy;

    /**
     * 验证是否重复
     * @author Caesar Liu
     * @date 2021-05-25 11:05
     */
    public Boolean isDuplicate(HttpServletRequest request, Long interval) {
        String requestKey = this.sign(request);
        Long requestTime = cacheProxy.get(requestKey);
        if (requestTime == null) {
            return Boolean.FALSE;
        }
        if (System.currentTimeMillis() - requestTime > interval) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 参数签名
     * @author Caesar Liu
     * @date 2021-05-25 11:39
     */
    public abstract String sign (HttpServletRequest request);
}
