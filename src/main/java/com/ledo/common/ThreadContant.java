package com.ledo.common;

import static javax.management.timer.Timer.*;

/**
 * 线程相关常量
 * @author qgl
 * @date 2018/11/14
 */
public class ThreadContant {
    // 线程池参数
    /** 核心线程池大小 */
    public static final int CORE_POOL_SIZE = 3;
    /** 自动更新任务线程 线程池名称前缀 */
    public static final String NAME_PREVIOUS_UPDATE_DATA_POOL = "kof_world";
    /** 监控任务单线程 线程池名称前缀 */
    public static final String NAME_PREVIOUS_MONITOR_POOL = "monitor";

    // 线程名称
    /** 更新网页内容线程名称 */
    public static final String URLCONTENT_THREAD_NAME = "UrlContentTask";
    /** 更新网页内容线程名称 */
    public static final String SERVERINFO_THREAD_NAME = "ServerInfoTask";
    /** 更新服务器开服天数线程名称 */
    public static final String SERVER_OPEN_DAYS_THREAD_NAME = "ServerOpenDays";
    /** 检测线程状态 */
    public static final String MONITOR_THREAD_NAME = "MonitorTask";

    // 开启线程检测的时间配置
    /** 检测合适的时间，最多检测 20 分钟，否则超时立即执行线程，防止出现死循环 */
    public static final long MONITOR_TIME_OUT = 20 * ONE_MINUTE;
    /** 检测到合适的时间，该时间的最大误差: 0.5 秒 */
    public static final long MAX_ERROR_RANGE = 500;
    /** 保存网页内容的周期：1 分钟 */
    public static final long SAVE_URLCONTENT_PERIOD = ONE_MINUTE;
    /** 检测线程池运行状态任务的周期：1 小时 */
    public static final long MONITOR_THREAD_POOL_PERIOD = ONE_HOUR;
    /** 检测线程池运行状态任务的周期：每天 */
    public static final long UPDATE_SERVER_OPEN_DAYS_PERIOD = ONE_DAY;
    /** 保存服务器信息的周期：10 分钟 ，延迟时间也为 10 分钟，达到期望的保存服务器时间点 xx:xx:00*/
    public static final long SAVE_SERVER_INFO_PERIOD = 10 * ONE_MINUTE;
    /** 更新服务器开服天数任务的时间点，每天的 11:00:10 */
    public static final String UPDATE_SERVER_OPEN_DAYS_TIME = " 11:00:10";
    /** 检测线程池运行状态任务的开始延迟：1小时10秒，达到期望的检测时间点 xx:xx:20 */
    public static final long MONITOR_THREAD_POOL_DELAY = ONE_HOUR + 20 * ONE_SECOND;
    /** 保存网页内容任务的延迟时间：27 秒，因为保存网页内容到数据库的SQL语句需要2.7秒左右
     这两个时间加起来一共延迟30秒左右，就可以达到更新完成时间是 xx:xx:30 的期望时间 */
    public static final long SAVE_URLCONTENT_TASK_INITIALDELAY = 27 * ONE_SECOND;

}
