package com.eva.core.annotation.pr;

import com.eva.service.proxy.CacheProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

/**
 * 防重复提交实现接口
 * @author Eva.Caesar Liu
 * @date 2021-05-25 10:58
 */
public abstract class PreventRepeatAdapter {

    // 请求Key前缀
    private static final String REQUEST_KEY_PREFIX = "eva:pr:";

    // 暴力请求Key前缀
    private static final String MASSIVE_KEY_PREFIX = "eva:massive:";

    @Autowired
    private CacheProxy<String, Object> cacheProxy;

    /**
     * 验证是否重复
     */
    public Boolean prevent(HttpServletRequest request, int interval) {
        String requestKey = REQUEST_KEY_PREFIX + this.sign(request);
        boolean isRepeat = cacheProxy.get(requestKey) != null;
        if (!isRepeat) {
            cacheProxy.put(requestKey, System.currentTimeMillis(), Long.valueOf(interval));
        }
        return isRepeat;
    }

    /**
     * 验证是否暴力涌入
     */
    public Boolean massive (HttpServletRequest request, int maxFreq, int lockTime) {
        String massiveKey = MASSIVE_KEY_PREFIX + this.sign(request);
        Object unit = cacheProxy.get(massiveKey);
        if (unit == null) {
            cacheProxy.put(massiveKey, new MassiveUnit(maxFreq, lockTime), 60);
            return Boolean.FALSE;
        }
        MassiveUnit massiveUnit = (MassiveUnit) unit;
        if (massiveUnit.getLock() == 1) {
            return Boolean.TRUE;
        }
        if (massiveUnit.getFreq() >= massiveUnit.getMaxFreq()) {
            massiveUnit.setLock((byte)1);
            cacheProxy.put(massiveKey, massiveUnit, Long.valueOf(massiveUnit.getLockTime()));
            return Boolean.TRUE;
        }
        massiveUnit.increment();
        cacheProxy.put(massiveKey, massiveUnit);
        return Boolean.FALSE;
    }


    /**
     * 参数签名
     */
    public abstract String sign (HttpServletRequest request);

    /**
     * 暴力验证单元
     */
    @Data
    static class MassiveUnit implements Serializable {

        private byte lock = 0;

        // 请求频率
        private int freq = 1;

        // 最高频率
        private int maxFreq;

        // 锁定时长
        private int lockTime;

        MassiveUnit (int maxFreq, int lockTime) {
            this.maxFreq = maxFreq;
            this.lockTime = lockTime;
        }

        void increment () {
            this.freq++;
        }
    }
}
