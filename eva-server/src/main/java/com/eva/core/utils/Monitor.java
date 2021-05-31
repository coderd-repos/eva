package com.eva.core.utils;

import com.eva.core.utils.monitor.Memory;
import com.sun.jna.platform.mac.DiskArbitration;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author Eva
 * @date 2021-04-13 22:34
 */
@Data
@ApiModel("监听信息")
public class Monitor implements Serializable {

    @ApiModelProperty(value = "操作系统名称")
    private String osName;

    @ApiModelProperty(value = "操作系统架构")
    private String osArch;

    @ApiModelProperty(value = "操作系统版本")
    private String osVersion;

    @ApiModelProperty(value = "服务器IP地址")
    private String ip;

    @ApiModelProperty(value = "MAC地址")
    private String mac;

    @ApiModelProperty(value = "服务器时间")
    private Date currentTime;

    @ApiModelProperty(value = "内存信息")
    private Memory memory;

    @ApiModelProperty(value = "CPU信息")
    private CPU cpu;

    @ApiModelProperty(value = "磁盘列表")
    private List<DiskArbitration> disks;

    @ApiModelProperty(value = "JVM信息")
    private JVM jvm;

    public Monitor() {
        oshi.SystemInfo systemInfo = new oshi.SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        systemInfo.getOperatingSystem().getSystemBootTime();
        // 系统信息
        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
        this.setOsName(osMXBean.getName());
        this.setOsVersion(osMXBean.getVersion());
        this.setOsArch(osMXBean.getArch());
        this.setIp(Utils.SERVER.getIP());
        this.setMac(Utils.SERVER.getMAC());
        this.setCurrentTime(new Date(System.currentTimeMillis()));
        this.setMemory(hardware.getMemory());
        this.setCpu(hardware.getProcessor());
        this.setJvm();
        this.setDisks(systemInfo.getOperatingSystem());
    }

    /**
     * 设置内存信息
     */
    private void setMemory(GlobalMemory memory) {
        this.memory = new Memory();
        this.memory.setSize(this.toM(memory.getTotal()));
        this.memory.setFreeSpace(this.toM(memory.getAvailable()));
    }

    /**
     * 设置CPU信息
     * 技术参考：https://blog.csdn.net/u012796085/article/details/104068769
     */
    private void setCpu(CentralProcessor processor) {
        this.cpu = new CPU();
        cpu.setLogicalCount(processor.getLogicalProcessorCount());
        cpu.setPhysicalCount(processor.getPhysicalProcessorCount());
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        try {
            TimeUnit.SECONDS.sleep(1);
            long[] ticks = processor.getSystemCpuLoadTicks();
            long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
            long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
            long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
            long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
            long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
            long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
            long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
            long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
            long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
            cpu.setSystemRatio(cSys * 100.0 / totalCpu);
            cpu.setUserRatio(user * 100.0 / totalCpu);
            cpu.setUseRatio(100.0 - idle * 100.0 / totalCpu);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置JVM信息
     */
    private void setJvm() {
        this.jvm = new JVM();
        Properties props = System.getProperties();
        jvm.setMemory(new Memory());
        jvm.getMemory().setSize(this.toM(Runtime.getRuntime().totalMemory()));
        jvm.getMemory().setFreeSpace(this.toM(Runtime.getRuntime().freeMemory()));
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
        jvm.setBootTime(new Date(ManagementFactory.getRuntimeMXBean().getStartTime()));
    }

    /**
     * 设置磁盘信息
     */
    private void setDisks(OperatingSystem os) {
        try {
            this.disks = new LinkedList<>();
            FileSystem fileSystem = os.getFileSystem();
            List<OSFileStore> fsArray = fileSystem.getFileStores();
            for (OSFileStore fileStore : fsArray) {
                Disk disk = new Disk();
                disk.setName(fileStore.getName());
                disk.setDir(fileStore.getMount());
                disk.setFsType(fileStore.getType());
                disk.setSize(this.toM(fileStore.getTotalSpace()));
                disk.setFreeSpace(this.toM(fileStore.getFreeSpace()));
                this.disks.add(disk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转为兆
     */
    private double toM(long value) {
        return value * 1.0 / 1024 / 1024;
    }
}
