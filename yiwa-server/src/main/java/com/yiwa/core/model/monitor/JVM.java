package com.yiwa.core.model.monitor;

import lombok.Data;

import java.util.Date;

/**
 * JVM信息
 * @author Yiwa
 * @date 2021-04-13 19:52
 */
@Data
public class JVM {

    // 版本
    private String version;

    // 安装路径
    private String home;

    // 启动时间
    private Date bootTime;

    // 内存信息
    private Memory memory;

    // 获取运行时长
    public String getRuntime () {
        long ms = (System.currentTimeMillis() - bootTime.getTime())/1000;
        long months = ms/2592000; // 一个月按30天计算
        long days = ms%2592000/86400;
        long hours = ms%2592000%86400/3600;
        long minutes = ms%2592000%86400%3600/60;
        String runtime = "";
        if (months > 0) {
            runtime += months + "个月";
        }
        if (days > 0 || months > 0) {
            runtime += days + "天";
        }
        if (hours > 0 || days > 0 || months > 0) {
            runtime += hours + "小时";
        }
        runtime += minutes + "分钟";
        return runtime;
    }
}
