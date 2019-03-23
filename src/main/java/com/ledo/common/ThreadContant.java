package com.ledo.common;

/**
 * 线程相关常量
 * @author qgl
 * @date 2018/9/24
 */
public class ThreadContant {
    // 线程池参数
    /** 核心线程池大小 */
    public static final int CORE_POOL_SIZE = 2;
    /** 自动更新任务线程 线程池名称前缀 */
    public static final String NAME_PREVIOUS_UPDATE_DATA_POOL = "kof_world";
    /** 监控任务单线程 线程池名称前缀 */
    public static final String NAME_PREVIOUS_MONITOR_POOL = "monitor";

    // 线程名称
    /** 更新网页内容线程名称 */
    public static final String URLCONTENT_THREAD_NAME = "UrlContentTask";
    /** 更新网页内容线程名称 */
    public static final String SERVERINFO_THREAD_NAME = "ServerInfoTask";
    /** 检测线程状态 */
    public static final String MONITOR_THREAD_NAME = "MonitorTask";

    // 开启线程检测的时间配置
    /** 时间常量，单位:ms */
    public static final int SECOND = 1000;
    public static final int MINUTE = 60 * SECOND;
    /** 检测线程池运行状态任务的周期：1 小时 */
    public static final int HOUR = 60 * MINUTE;
    /** 检测合适的时间，最多检测 20 分钟，否则超时立即执行线程，防止出现死循环 */
    public static final int MONITOR_TIME_OUT = 20 * MINUTE;
    /** 检测到合适的时间，该时间的最大误差: 0.5 秒 */
    public static final int MAX_ERROR_RANGE = 500;
    /** 保存服务器信息的周期：10 分钟 */
    public static final int SAVE_SERVER_INFO_PERIOD = 10 * MINUTE;
    /** 保存网页内容的周期：1 分钟 */
    public static final int SAVE_URLCONTENT_PERIOD = MINUTE;
    /** 保存网页内容任务的延迟时间：27 秒，因为保存网页内容到数据库的SQL语句需要2.7秒左右
     这两个时间加起来一共延迟30秒左右，就可以达到更新时间是 xx:xx:30 的期望时间 */
    public static final int URLCONTENT_TASK_INITIALDELAY = 27 * SECOND;

}
