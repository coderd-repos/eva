package com.yiwa.core.model.monitor;

import lombok.Data;
import lombok.ToString;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 系统信息
 * @author Yiwa
 * @date 2021-04-13 20:34
 */
@Data
@ToString
public class SystemInfo {

    // 操作系统名称
    private String osName;

    // 操作系统架构
    private String osArch;

    // 操作系统版本
    private String osVersion;

    // IP地址
    private String ip;

    // MAC地址
    private String mac;

    // 服务器时间
    private Date currentTime;

    // 内存信息
    private Memory memory;

    // CPU信息
    private CPU cpu;

    // 磁盘信息
    private List<Disk> disks;

    // JVM信息
    private JVM jvm;

    public SystemInfo() {
        oshi.SystemInfo systemInfo = new oshi.SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        systemInfo.getOperatingSystem().getSystemBootTime();
        // 系统信息
        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
        this.setOsName(osMXBean.getName());
        this.setOsVersion(osMXBean.getVersion());
        this.setOsArch(osMXBean.getArch());
        this.setIp(this.getIpAddress());
        this.setMac(this.getMacAddress());
        this.setCurrentTime(new Date(System.currentTimeMillis()));
        this.setMemory(hardware.getMemory());
        this.setCpu(hardware.getProcessor());
        this.setJvm();
        this.setDisks(systemInfo.getOperatingSystem());
    }

    public static void main(String[] args) {
        System.out.println(new SystemInfo());
    }

    /**
     * 设置内存信息
     * @author Yiwa
     * @date 2021-04-13 22:34
     */
    private void setMemory(GlobalMemory memory) {
        this.memory = new Memory();
        this.memory.setSize(this.toM(memory.getTotal()));
        this.memory.setFreeSpace(this.toM(memory.getAvailable()));
    }

    /**
     * 设置CPU信息
     * @author Yiwa
     * @date 2021-04-13 22:34
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
     * @author Yiwa
     * @date 2021-04-13 22:34
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
     * @author Yiwa
     * @date 2021-04-13 22:02
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
     * 获取InetAddress对象
     * @author Yiwa
     * @date 2021-04-14 20:45
     * 技术参考：https://blog.csdn.net/weixin_37738830/article/details/100108266
     */
    private InetAddress getInetAddress () {
        InetAddress inetAddress = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (name.contains("docker") || name.contains("lo")) {
                    return inetAddress;
                }
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress ia = enumIpAddr.nextElement();
                    if (ia.isLoopbackAddress()) {
                        continue;
                    }
                    String address = ia.getHostAddress();
                    if (address.contains("::") || address.contains("0:0:") || address.contains("fe80")) {
                        continue;
                    }
                    if (!"127.0.0.1".equals(ip)) {
                        inetAddress = ia;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return inetAddress;
    }

    /**
     * 获取IP地址
     * @author Yiwa
     * @date 2021-04-13 21:14
     */
    private String getIpAddress() {
        String ip = "获取失败";
        InetAddress inetAddress = this.getInetAddress();
        if (inetAddress != null) {
            ip = inetAddress.getHostAddress();
        }
        return ip;
    }

    /**
     * 获取MAC地址
     * @author Yiwa
     * @date 2021-04-13 21:17
     */
    private String getMacAddress() {
        try {
            InetAddress inetAddress = this.getInetAddress();
            if (inetAddress == null) {
                return "获取失败";
            }
            byte[] bs = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < bs.length; i++) {
                if (i != 0) {
                    sb.append(":");
                }
                //字节转换为整数
                int temp = bs[i] & 0xff;
                // 把无符号整数参数所表示的值转换成以十六进制表示的字符串
                String str = Integer.toHexString(temp);
                if (str.length() == 1) {
                    sb.append("0" + str);
                } else {
                    sb.append(str);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            return "获取失败";
        }
    }

    /**
     * 转为兆
     * @author Yiwa
     * @date 2021-04-13 21:50
     */
    private double toM(long value) {
        return value * 1.0 / 1024 / 1024;
    }

}
