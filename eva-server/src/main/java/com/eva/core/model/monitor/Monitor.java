package com.eva.core.model.monitor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Eva
 * @date 2021-04-13 22:34
 */
@ApiModel("监听信息")
public class Monitor {

    @ApiModelProperty(value = "系统信息")
    public static SystemInfo getSystemInfo () {
        return new SystemInfo();
    }
}
