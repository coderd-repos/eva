package com.eva.core.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * 地区工具类
 * 技术参考：https://blog.csdn.net/jam_fanatic/article/details/82822878
 * 第三方服务：http://whois.pconline.com.cn/
 * @author Eva.Caesar Liu
 * @date 2021-05-31 14:51
 */
@Slf4j
public class Location {

    // 地区API
    private static final String GET_LOCATION_API = "http://whois.pconline.com.cn/ipJson.jsp?json=true&ip={}";

    /**
     * 获取地区
     * @param ip IP
     *
     * @return Info
     */
    public Info getLocation (String ip) {
        try {
            return Utils.Http.build(String.format(GET_LOCATION_API, ip), Charset.forName("GBK").toString())
                    .setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9")
                    .setRequestProperty("Accept-Encoding", "gzip, deflate")
                    .gzip()
                    .get()
                    .toClass(Info.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取地区
     * @param ip IP
     *
     * @return String
     */
    public String getLocationString (String ip) {
        Info info = this.getLocation(ip);
        if (info == null) {
            return "UNKNOWN";
        }
        return info.getAddr();
    }

    /**
     * 地区信息包装
     */
    @Data
    public static class Info implements Serializable {

        private String pro;

        private String proCode;

        private String city;

        private String cityCode;

        private String addr;

    }
}
