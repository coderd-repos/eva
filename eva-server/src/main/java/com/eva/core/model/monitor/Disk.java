package com.eva.core.model.monitor;

import lombok.Data;

/**
 * 磁盘信息
 * @author Eva
 * @date 2021-04-13 20:03
 */
@Data
public class Disk {

    // 磁盘名称
    private String name;

    // 盘符路径
    private String dir;

    // 文件系统类型
    private String fsType;

    // 总大小
    private double size;

    // 可用大小
    private double freeSpace;

    /**
     * 获取已使用大小
     * @author Eva
     * @date 2021-04-13 20:12
     */
    public double getUsedSpace () {
        return size - freeSpace;
    }

    /**
     * 获取使用率
     * @author Eva
     * @date 2021-04-13 20:13
     */
    public double getUseRatio () {
        return getUsedSpace() / size * 100;
    }
}
