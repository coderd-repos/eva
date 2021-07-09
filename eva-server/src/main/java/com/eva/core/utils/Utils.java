package com.eva.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 工具包
 * @author Eva.Caesar Liu
 * @date 2021-05-31 15:19
 */
@Component
public final class Utils {

    /**
     * OSS操作
     */
    public static OSS OSS;

    /**
     * 地区处理
     */
    public static final Location Location = new Location();

    /**
     * Http请求处理
     */
    public static final Http Http = new Http();

    /**
     * 用户客户端信息
     */
    public static final UserClient User_Client = new UserClient();

    /**
     * 服务端信息
     */
    public static final Server Server = new Server();

    /**
     * 监听器
     */
    public static final Monitor Monitor = new Monitor();

    /**
     * MyBatis Plus处理
     */
    public static final MyBatisPlus MP = new MyBatisPlus();

    /**
     * 安全处理
     */
    public static final Secure Secure = new Secure();

    /**
     * 日期处理
     */
    public static final Date Date = new Date();

    @Autowired
    public void setOSS (OSS oss) {
        Utils.OSS = oss;
    }

}
