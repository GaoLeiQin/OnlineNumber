package com.ledo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.management.timer.Timer.*;

/**
 * 日期的工具类
 * @author qgl
 * @date 2018/11/16
 */
public class DateUtil {

    /** 日期串格式 */
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 根据毫秒数(String)返回标准的日期串
     * @param timeString 毫秒
     * @return 标准日期
     */
    public static String getFormatDateByMillSecond(String timeString) {
        Date date = new Date(Long.valueOf(timeString));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        return simpleDateFormat.format(date);
    }

    /**
     * 根据毫秒数(long)返回标准的日期串
     * @param time 毫秒
     * @return 标准日期
     */
    public static String getFormatDateByMillSecond(long time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        return simpleDateFormat.format(date);
    }

    /**
     * 将日期串转换毫秒数
     * @param strTime 字符串
     * @return 毫秒
     */
    public static long getMilliSecondByFormatDate(String strTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        try {
            return simpleDateFormat.parse(strTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前时间的日期串
     * @return 日期的类型：yyyy-MM-dd HH:mm:ss
     */
    public static String getNowFormatDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前距离整点（xx:x0:00）的时间
     * @return
     */
    public static String getWaitingTime(long integerNumber) {
        String remainSeconds = null;
        long now = System.currentTimeMillis();
        for (long i = now; i < now + integerNumber; i++) {
            if (i % integerNumber == 0) {
                remainSeconds = getRemainTime(i - now);
            }
        }
        return remainSeconds;
    }

    /**
     * 获取当前时间与输入时间间隔多少天
     * @param formatDate
     * @return
     */
    public static int getIntervalDays(String formatDate) throws ParseException {
        int openDays = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        long openTime = simpleDateFormat.parse(formatDate).getTime();
        String remainTime = getRemainTime(System.currentTimeMillis() - openTime);
        int index = remainTime.indexOf("天");
        // 判断间隔天数是否大于1天
        if (index > 0) {
            openDays = Integer.valueOf(remainTime.substring(0, index));
        }
        return openDays;
    }

    /**
     * 将毫秒数转换为 x天x小时x分钟x秒
     * @param time
     * @return
     */
    public static String getRemainTime(long time) {
        String remainTime = " ";
        long second = 0;
        long minute = 0;
        long hour = 0;
        long day = 0;

        if (time <= ONE_MINUTE) {
            second = time / ONE_SECOND;
            remainTime = second + "秒";
        }else if (time <= ONE_HOUR){
            minute = time / ONE_MINUTE;
            second = (time % ONE_MINUTE) / ONE_SECOND;
            remainTime = minute + "分 " + second + "秒";
        }else if (time <= ONE_DAY){
            hour = time / ONE_HOUR;
            minute = (time % ONE_HOUR) / ONE_MINUTE;
            second = (time % ONE_MINUTE) / ONE_SECOND;
            remainTime = hour + "小时 " + minute + "分钟 " + second + "秒";
        }else{
            day = time / ONE_DAY;
            hour = (time % ONE_DAY) / ONE_HOUR;
            minute = (time % ONE_HOUR) / ONE_MINUTE;
            second = (time % ONE_MINUTE) / ONE_SECOND;
            remainTime = day + "天 " + hour + "小时 " + minute + "分钟 " + second + "秒";
        }

        return remainTime;
    }

}
