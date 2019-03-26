package com.ledo.task;

import com.ledo.dao.IOnlineNumber;
import com.ledo.dao.IUrlContent;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static com.ledo.common.ThreadContant.CORE_POOL_SIZE;

/**
 * 监测线程池运行状态任务
 * @author qgl
 * @date 2018/11/14
 */
public class MonitorThreadPoolTask extends Task {
    private ScheduledThreadPoolExecutor scheduledExecutor = null;

    public MonitorThreadPoolTask(ScheduledThreadPoolExecutor scheduledExecutor, IOnlineNumber onlineNumberDao, IUrlContent urlContentDao) {
        this.scheduledExecutor = scheduledExecutor;
        this.onlineNumberDao = onlineNumberDao;
        this.urlContentDao = urlContentDao;
    }

    @Override
    public void run() {
        this.inspectThreadPoolState();
    }

    /**
     * 定期检查线程池的运行状态
     */
    public void inspectThreadPoolState() {
        long alltTaskCount = this.scheduledExecutor.getTaskCount();
        long completeCount = this.scheduledExecutor.getCompletedTaskCount();
        long waittingTaskCount = alltTaskCount - completeCount;
        int needRunningTaskCount = CORE_POOL_SIZE - 1;
        if (waittingTaskCount < needRunningTaskCount) {
            int stopTaskCount = this.restart(onlineNumberDao, urlContentDao);
            logger.error("当前等待运行的任务 " + waittingTaskCount + " 个，需要一直运行的任务 " +
                    needRunningTaskCount + " 个，所以必须重启自动更新线程！！！已暂停等待执行任务 " + stopTaskCount + " 个");
        }else {
            logger.info("线程池工作正常，正在等待运行的任务 " + waittingTaskCount + " 个");
        }
    }

    /**
     * 重启自动更新线程
     * @return 已暂停等待执行任务数量
     */
    public int restart(IOnlineNumber onlineNumberDao, IUrlContent urlContentDao) {
        int stopTaskCount = -1;
        if (this.scheduledExecutor.getTaskCount() > 0) {
            List<Runnable> runnableList = this.scheduledExecutor.shutdownNow();
            stopTaskCount = runnableList.size();
        }else {
            this.scheduledExecutor = null;
        }
        AllTask.getInstance().startAutoUpdateTask(onlineNumberDao, urlContentDao);
        return stopTaskCount;
    }
}
