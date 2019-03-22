package com.ledo.manager;

import com.ledo.beans.ServerHistoryInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件相关操作
 * @author qgl
 * @date 2018/9/24
 */
public class FileManager {
    /**
     * 获取开服天数
     * @param serverOpenTime
     * @return
     */
    public static int getServerOpenDays(String serverOpenTime) {
        int openDays = 0;
        long now = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long openTime = 0;
        try {
            openTime = simpleDateFormat.parse(serverOpenTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        openDays = getFormatTime(now - openTime);
        return openDays;
    }

    /**
     * 将毫秒数转换为 x天x小时x分钟x秒
     * @param time
     * @return
     */
    private static int getFormatTime(long time) {
        long second = time / 1000;
        long minute = 0;
        long hour = 0;
        long day = 0;

        if (second > 60) {
            minute = second / 60;
            second = second % 60;
            if (minute > 60) {
                hour = minute / 60;
                minute = minute % 60;
                if (hour > 24) {
                    day = hour / 24;
                    hour = hour % 24;
                }
            }
        }

        return (int) day;
    }

    /**
     * 获取当前时间的日期串
     * @return 日期的类型：yyyy-MM-dd HH:mm:ss
     */
    public static String getNowFormatDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取字符串中的所有数字
     * @param category
     * @return int
     */
    public static int getNumber(String category) {
        String numberRegex = "[^0-9]";
        Pattern p = Pattern.compile(numberRegex);
        Matcher m = p.matcher(category);
        String target = m.replaceAll("");
        return Integer.valueOf(target);
    }

    /**
     * 获取历史数据中的日期
     * @param historyInfos
     * @return
     */
    public ArrayList<String> getStrTime(ArrayList<ServerHistoryInfo> historyInfos) {
        ArrayList<String> strTime = new ArrayList<>(25);
        for (ServerHistoryInfo history : historyInfos) {
            strTime.add(history.getDate());
        }
        return strTime;
    }

    /**
     * 将日期串转换毫秒数
     * @param strTime 字符串
     * @return 毫秒
     */
    public static long getMilliSecondByFormatDate(String strTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(strTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据毫秒数返回标准的日期串
     * @param timeString 毫秒
     * @return 标准日期
     */
    public static String getFormatDateByMillSecond(String timeString) {
        Date date = new Date(Long.valueOf(timeString));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 通过文件名截取到 zoneId
     * @param name 文件名
     * @return zoneId
     */
    public static int getZoneIdByFilename(String name) {
        int zoneId = 0;
        String fileName = name.substring(39).split("\\.")[0];
        if (fileName.contains("gat")) {
            String[] gatNames = fileName.split("_");
            zoneId =  Integer.valueOf(gatNames[gatNames.length - 1]);
        }else {
            String[] names = fileName.split("=");
            zoneId = Integer.valueOf(names[0]);
        }

        return zoneId;
    }
}
