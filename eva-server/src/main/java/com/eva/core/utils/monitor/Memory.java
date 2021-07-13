package com.eva.core.utils.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Data
@ApiModel("内存信息")
public class Memory implements Serializable {

    @ApiModelProperty(value = "总内存")
    private double size;

    @ApiModelProperty(value = "空闲内存")
    private double freeSpace;

    @ApiModelProperty(value = "获取已使用大小")
    public double getUsedSpace () {
        return size - freeSpace;
    }

    @ApiModelProperty(value = "获取使用率")
    public double getUseRatio () {
        return getUsedSpace() / size * 100;
    }
}
