package com.yiwa.core.model.monitor;

import lombok.Data;

/**
 * 内存信息
 * @author Yiwa
 * @date 2021-04-13 20:09
 */
@Data
public class Memory {

    // 总内存
    private double size;

    // 空闲内存
    private double freeSpace;

    /**
     * 获取已使用大小
     * @author Yiwa
     * @date 2021-04-13 20:10
     */
    public double getUsedSpace () {
        return size - freeSpace;
    }

    /**
     * 获取使用率
     * @author Yiwa
     * @date 2021-04-13 20:10
     */
    public double getUseRatio () {
        return getUsedSpace() / size * 100;
    }
}
