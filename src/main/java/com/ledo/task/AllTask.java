package com.ledo.task;

import com.ledo.manager.DateManager;
import com.ledo.service.IAllServerInfoService;
import com.ledo.service.IOnlineNumberService;
import com.ledo.service.IUrlContentService;
import com.ledo.util.DateUtil;
import org.apache.log4j.Logger;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ledo.common.ThreadContant.*;

/**
 * 所有的自动更新任务
 * @author qgl
 * @date 2018/11/14
 */
public class AllTask {
    private Logger logger = Logger.getLogger(AllTask.class);
    private static AllTask instance = new AllTask();
    private ScheduledThreadPoolExecutor scheduledExecutor = null;

    public static AllTask getInstance() {
        return instance;
    }

    /**
     * 开启自动更新任务（包括网页内容和添加服务器信息）
     * @param onlineNumberService
     */
    public void startAutoUpdateTask(IUrlContentService urlContentService, IOnlineNumberService onlineNumberService, IAllServerInfoService allServerInfoService) {
        scheduledExecutor = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE, new MyThreadFactory(NAME_PREVIOUS_UPDATE_DATA_POOL));
        SaveUrlContentTask urlContentTask = new SaveUrlContentTask(urlContentService);
        urlContentTask.setThreadName(URLCONTENT_THREAD_NAME);
        SaveServerInfoTask serverInfoTask = new SaveServerInfoTask(onlineNumberService);
        serverInfoTask.setThreadName(SERVERINFO_THREAD_NAME);
        UpdateServerOpenDaysTask updateServerOpenDaysTask = new UpdateServerOpenDaysTask(allServerInfoService);
        updateServerOpenDaysTask.setThreadName(SERVER_OPEN_DAYS_THREAD_NAME);

        long beforeTime = System.currentTimeMillis();
        boolean isCheckPeriod = true;
        while (isCheckPeriod) {
            long now = System.currentTimeMillis();
            boolean isTimeOut = now - beforeTime > MONITOR_TIME_OUT;
            if (now % SAVE_SERVER_INFO_PERIOD < MAX_ERROR_RANGE || isTimeOut) {
                isCheckPeriod = false;
                scheduledExecutor.scheduleAtFixedRate(urlContentTask, SAVE_URLCONTENT_TASK_INITIALDELAY, SAVE_URLCONTENT_PERIOD, TimeUnit.MILLISECONDS);
                scheduledExecutor.scheduleAtFixedRate(serverInfoTask, SAVE_SERVER_INFO_PERIOD, SAVE_SERVER_INFO_PERIOD, TimeUnit.MILLISECONDS);
                long updateServerOpenDayDelay = DateManager.getInstance().getUpdateServerOpenDaysTaskDelayTime(now, UPDATE_SERVER_OPEN_DAYS_TIME);
                scheduledExecutor.scheduleAtFixedRate(updateServerOpenDaysTask, updateServerOpenDayDelay, UPDATE_SERVER_OPEN_DAYS_PERIOD, TimeUnit.MILLISECONDS);
                logger.info("开启3个自动更新定时任务成功， " + DateUtil.getRemainTime(SAVE_URLCONTENT_TASK_INITIALDELAY) + "后第一次执行自动更新网页内容任务， " +
                        DateUtil.getRemainTime(SAVE_SERVER_INFO_PERIOD) + "后，第一次执行自动添加服务器在线人数信息任务， " + DateUtil.getRemainTime(updateServerOpenDayDelay) + "后第一次执行自动更新服务器开服天数任务");
            }

            if (isTimeOut) {
                logger.error(" 立即开启任务！！！，(TimeOut) 已经寻找了 " + MONITOR_TIME_OUT + " ms，但没有找到合适的时间开启线程！");
            }
        }
    }

    /**
     * 监视线程任务
     * @param onlineNumberService
     */
    public void executeMonitorThreadTask(IUrlContentService urlContentService, IOnlineNumberService onlineNumberService, IAllServerInfoService allServerInfoService) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new MyThreadFactory(NAME_PREVIOUS_MONITOR_POOL));
        MonitorThreadPoolTask monitorThreadPoolTask = new MonitorThreadPoolTask(this.scheduledExecutor, urlContentService, onlineNumberService, allServerInfoService);
        monitorThreadPoolTask.setThreadName(MONITOR_THREAD_NAME);
        executorService.scheduleAtFixedRate(monitorThreadPoolTask, MONITOR_THREAD_POOL_DELAY, MONITOR_THREAD_POOL_PERIOD, TimeUnit.MILLISECONDS);
        logger.info("监视线程已启动！");
    }

    /**
     * 自定义 ThreadFactory，可设置线程池名称
     */
    private class MyThreadFactory implements ThreadFactory {
        private final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        MyThreadFactory(String namePre) {
            SecurityManager scheduledExecutor = System.getSecurityManager();
            group = (scheduledExecutor != null) ? scheduledExecutor.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = namePre + " pool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            t.setDaemon(false);
            t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
