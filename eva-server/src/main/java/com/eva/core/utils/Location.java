package com.eva.core.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 地区工具类
 * 技术参考：https://blog.csdn.net/jam_fanatic/article/details/82822878
 * 第三方服务：http://whois.pconline.com.cn/
 * @author Eva
 * @date 2021-05-31 14:51
 */
@Slf4j
class Location {

    // 地区API
    private static final String GET_LOCATION_API = "http://whois.pconline.com.cn/ipJson.jsp";

    /**
     * 获取地区
     */
    Info getLocation (String ip) {
        try {
            JSONObject location = Util.http.build(GET_LOCATION_API, "GBK")
                    .get(new HashMap<String, String>(){{
                        this.put("json", "true");
                        this.put("ip", ip);
                    }})
                    .toJSONObject();
            System.out.println(location);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 地区信息包装
     */
    public static class Info implements Serializable {

    }

    public static void main(String[] args) {
        new Location().getLocation("42.49.254.153");
    }
}
