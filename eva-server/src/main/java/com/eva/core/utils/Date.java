package com.eva.core.utils;

import java.util.Calendar;

/**
 * 日期工具
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
public final class Date {

    /**
     * 获取日期的开始时间
     * @param date 日期
     *
     * @return java.util.Date
     */
    public java.util.Date getStart (java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取日期的结束时间
     * @param date 日期
     *
     * @return java.util.Date
     */
    public java.util.Date getEnd (java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
