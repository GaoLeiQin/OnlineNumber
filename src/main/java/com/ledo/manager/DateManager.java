package com.ledo.manager;

import com.ledo.beans.AllServerInfo;
import com.ledo.util.DateUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import static javax.management.timer.Timer.ONE_HOUR;
import static javax.management.timer.Timer.ONE_SECOND;

/**
 * manager 处理日期 相关方法
 * @author qgl
 * @date 2018/11/18
 */
public class DateManager extends BaseManager{
    private static final int UPDATE_SERVER_OPEN_DAYS_HOUR = 11;
    public static DateManager instance = new DateManager();

    public static DateManager getInstance() {
        return instance;
    }

    /**
     * 获取全部服务器的开服时间对应的开服天数
     * @param allServerInfoArrayList
     * @return
     */
    public HashMap<String, Integer> getOpenDaysByServerOpenTime(ArrayList<AllServerInfo> allServerInfoArrayList) {
        HashMap<String, Integer> openDaysMap = new HashMap<>(64);

        for (AllServerInfo serverInfo : allServerInfoArrayList) {
            String openTime = serverInfo.getOpenTime();
            openDaysMap.putIfAbsent(openTime, this.getServerOpenDays(openTime));
        }

        return openDaysMap;
    }

    /**
     * 获取开服天数
     * @param serverOpenTime
     * @return
     */
    public int getServerOpenDays(String serverOpenTime) {
        int openDays = 0;
        try {
            openDays = DateUtil.getIntervalDays(serverOpenTime);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("获取开服天数失败 " + e);
        }
        return openDays;
    }

    /**
     * 获取输入时间点距离目标时间点的时间
     * @param now 输入时间为毫秒数
     * @param aimTime 目标时间格式 ：xx:xx:xx
     * @return 间隔时间毫秒数
     */
    public long getUpdateServerOpenDaysTaskDelayTime(long now, String aimTime) {
        long delayTime = 0;
        String nowTime = DateUtil.getFormatDateByMillSecond(now);
        // 获取时间格式为的 yyyy-MM-dd 时间串
        String simpleFormatDate = nowTime.substring(0, 10);
        // 获取当前时间的xx小时
        int hour= Integer.valueOf(nowTime.substring(11, 13));
        if (hour == UPDATE_SERVER_OPEN_DAYS_HOUR && now % ONE_HOUR == 0) {
            // 当前时间刚好是11:00:00，延迟10秒
            delayTime = 10 * ONE_SECOND;
        }else if (hour < UPDATE_SERVER_OPEN_DAYS_HOUR) {
            // 如果还没到11点
            delayTime = DateUtil.getMilliSecondByFormatDate(simpleFormatDate + aimTime) - now;
        }else {
            // 如果今天已经过了11点，延后一天
            String afterDate = DateUtil.getAfterOneDay(simpleFormatDate);
            delayTime = DateUtil.getMilliSecondByFormatDate(afterDate + aimTime) - now;
        }

        return delayTime;
    }
}
