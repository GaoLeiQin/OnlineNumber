package com.ledo.task;

import com.ledo.service.IAllServerInfoService;
import com.ledo.service.IOnlineNumberService;
import com.ledo.service.IUrlContentService;
import org.apache.log4j.Logger;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 定时线程任务父类
 * @author qgl
 * @date 2018/11/13
 */
public abstract class BaseTask extends Thread {
    public Logger logger = Logger.getLogger(AllTask.class);

    public ScheduledThreadPoolExecutor scheduledExecutor;
    public IUrlContentService urlContentService;
    public IOnlineNumberService onlineNumberService;
    public IAllServerInfoService allServerInfoService;

    @Override
    public void run() {

    }

    /** 设置线程名称 */
    public void setThreadName(String threadName) {
        this.setName(threadName);
    }
}
