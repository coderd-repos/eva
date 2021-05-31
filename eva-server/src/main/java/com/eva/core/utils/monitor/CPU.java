package com.eva.core.utils.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * CPU信息
 * @author Eva.Caesar Liu
 * @date 2021-04-13 19:59
 */
@Data
@ApiModel("CPU信息")
public class CPU implements Serializable {

    @ApiModelProperty(value = "逻辑核数")
    private int logicalCount;

    @ApiModelProperty(value = "物理核数")
    private int physicalCount;

    @ApiModelProperty(value = "用户使用率")
    private double userRatio;

    @ApiModelProperty(value = "系统使用率")
    private double systemRatio;

    @ApiModelProperty(value = "当前使用率")
    private double useRatio;

    @ApiModelProperty(value = "空闲率")
    public double getFreeRatio () {
        return 100.0 - useRatio;
    }
}
