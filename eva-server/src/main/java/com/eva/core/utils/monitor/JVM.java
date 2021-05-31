package com.eva.core.utils.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Eva.Caesar Liu
 * @date 2021-04-13 19:52
 */
@Data
@ApiModel("JVM信息")
public class JVM implements Serializable {

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "安装路径")
    private String home;

    @ApiModelProperty(value = "启动时间")
    private Date bootTime;

    @ApiModelProperty(value = "内存信息")
    private Memory memory;

    @ApiModelProperty(value = "运行时长")
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
