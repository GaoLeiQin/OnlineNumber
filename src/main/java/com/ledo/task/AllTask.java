package com.ledo.task;

import com.ledo.dao.IAdministrator;
import com.ledo.dao.IUrlContent;
import org.apache.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        scheduledExecutor = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
        scheduledExecutor.scheduleAtFixedRate(new SaveUrlContentTask(urlContentDao), 1, 60, TimeUnit.SECONDS);
        scheduledExecutor.scheduleAtFixedRate(new SaveServerInfoTask(administratorDao, urlContentDao), 34, 60 * 10, TimeUnit.SECONDS);
        logger.info("开启定时任务成功！");
    }
}
