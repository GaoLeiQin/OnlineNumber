package com.ledo.task;

import com.ledo.dao.IAdministrator;
import com.ledo.dao.IUrlContent;
import org.apache.log4j.Logger;

/**
 * 定时线程任务父类
 * @author qgl
 * @date 2018/11/13
 */
public abstract class Task extends Thread {
    public Logger logger = Logger.getLogger(AllTask.class);

    public IAdministrator administratorDao;
    public IUrlContent urlContentDao;

    @Override
    public void run() {

    }

    /** 设置线程名称 */
    public abstract void setThreadName(String threadName);
}
