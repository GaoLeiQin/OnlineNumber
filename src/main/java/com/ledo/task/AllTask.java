package com.ledo.task;

import com.ledo.dao.IAdministrator;
import com.ledo.dao.IUrlContent;
import org.apache.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.ledo.common.ThreadContant.*;

/**
 * 所有的自动更新任务
 * @author qgl
 * @date 2018/11/14
 */
public class AllTask {
    private Logger logger = Logger.getLogger(AllTask.class);
    private static final AllTask instance = new AllTask();
    private static final int CORE_POOL_SIZE = 2;
    private ScheduledExecutorService scheduledExecutor = null;

    public static AllTask getInstance() {
        return instance;
    }

    /**
     * 开启自动更新任务（网页内容，添加服务器信息）
     * @param administratorDao
     * @param urlContentDao
     */
    public void openAutoUpdateTask(IAdministrator administratorDao, IUrlContent urlContentDao) {
        // 16:09:26
        // 16:19:26
        scheduledExecutor = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
        SaveUrlContentTask urlContentTask = new SaveUrlContentTask(urlContentDao);
        urlContentTask.setThreadName(URLCONTENT_THREAD_NAME);
        printThreadInfo(urlContentTask, "URL 之前：");
        SaveServerInfoTask serverInfoTask = new SaveServerInfoTask(administratorDao, urlContentDao);
        serverInfoTask.setThreadName(SERVERINFO_THREAD_NAME);
        printThreadInfo(serverInfoTask, "Server 之前：");

        long beforeTime = System.currentTimeMillis();
        boolean isOtherTime = true;
        while (isOtherTime) {
            long now = System.currentTimeMillis();
            boolean isTimeOut = now - beforeTime > MONITOR_TIME_OUT;
            if (now % SAVE_SERVER_INFO_PERIOD < MAX_ERROR_RANGE || isTimeOut) {
                scheduledExecutor.scheduleAtFixedRate(urlContentTask, URLCONTENT_TASK_INITIALDELAY, SAVE_URLCONTENT_PERIOD, TimeUnit.MILLISECONDS);
                scheduledExecutor.scheduleAtFixedRate(serverInfoTask, SAVE_SERVER_INFO_PERIOD, SAVE_SERVER_INFO_PERIOD, TimeUnit.MILLISECONDS);
                logger.info("开启定时任务成功！");
                printThreadInfo(urlContentTask, "URL 启动后：");
                printThreadInfo(serverInfoTask, "Server 启动后：");
                isOtherTime = false;
            }
        }

    }

    /**
     * 重启线程
     */
    public void restart(Task task) {
        task.interrupt();
    }

    public void printThreadInfo(Thread thread, String message) {
//        线程名称：UrlContentTask 线程Id：69 线程优先级：5 线程状态：NEW 线程组：java.lang.ThreadGroup[name=main,maxpri=10]，isAlive：false，isDaemon：true，isInterrupted：false
//        线程名称：ServerInfoTask 线程Id：70 线程优先级：5 线程状态：NEW 线程组：java.lang.ThreadGroup[name=main,maxpri=10]，isAlive：false，isDaemon：true，isInterrupted：false
        logger.info(message + " ，线程名称：" + thread.getName() + "，线程Id：" + thread.getId() + "，线程优先级：" + thread.getPriority() +
                " ，线程状态：" + thread.getState() + " ，线程组：" + thread.getThreadGroup() + "，isAlive："+ thread.isAlive() +
                "，isDaemon：" + thread.isDaemon() + "，isInterrupted：" + thread.isInterrupted());
    }
}
