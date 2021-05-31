package com.eva.core.utils.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author Eva
 * @date 2021-04-13 22:34
 */
@ApiModel("监听信息")
public class Monitor implements Serializable {

    @ApiModelProperty(value = "系统信息")
    public SystemInfo getSystemInfo () {
        return new SystemInfo();
    }
}
