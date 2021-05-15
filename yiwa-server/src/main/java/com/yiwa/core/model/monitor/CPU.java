package com.yiwa.core.model.monitor;

import lombok.Data;

/**
 * CPU信息
 * @author Caesar Liu
 * @date 2021-04-13 19:59
 */
@Data
public class CPU {

    // 逻辑核数
    private int logicalCount;

    // 物理核数
    private int physicalCount;

    // 用户使用率
    private double userRatio;

    // 系统使用率
    private double systemRatio;

    // 当前使用率
    private double useRatio;

    /**
     * 空闲率
     * @author Caesar Liu
     * @date 2021-04-13 20:17
     */
    public double getFreeRatio () {
        return 100.0 - useRatio;
    }
}
