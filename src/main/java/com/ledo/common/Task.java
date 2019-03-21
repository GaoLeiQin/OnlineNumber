package com.ledo.common;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时线程任务
 * @author qgl
 * @date 2018/11/13
 */
public class Task {
    private ScheduledExecutorService scheduledExecutor = null;
    public Task(int corePoolSize) {
        scheduledExecutor = Executors.newScheduledThreadPool(corePoolSize);
    }

    public void startScheduleRateTask(Runnable runnable) {
        scheduledExecutor.scheduleAtFixedRate(runnable, 1, 5, TimeUnit.SECONDS);
    }

    public void startScheduleDelayTask(Runnable runnable) {
        scheduledExecutor.scheduleWithFixedDelay(runnable, 3, 8, TimeUnit.SECONDS);

    }
}
