package com.eva.core.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class ServerUtil {

    /**
     * 获取InetAddress对象
     * 技术参考：https://blog.csdn.net/weixin_37738830/article/details/100108266
     */
    private static InetAddress getInetAddress () {
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
                    if (!"127.0.0.1".equals(address)) {
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
     */
    public static String getIpAddress() {
        String ip = "获取失败";
        InetAddress inetAddress = ServerUtil.getInetAddress();
        if (inetAddress != null) {
            ip = inetAddress.getHostAddress();
        }
        return ip;
    }

    /**
     * 获取MAC地址
     */
    public static String getMacAddress() {
        try {
            InetAddress inetAddress = ServerUtil.getInetAddress();
            if (inetAddress == null) {
                return "获取失败";
            }
            byte[] bs = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < bs.length; i++) {
                if (i != 0) {
                    sb.append(":");
                }
                // 字节转换为整数
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
}
