package com.ledo.task;

import com.ledo.manager.DateManager;
import com.ledo.service.IAllServerInfoService;
import com.ledo.service.IOnlineNumberService;
import com.ledo.service.IUrlContentService;
import com.ledo.util.DateUtil;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.ledo.common.ThreadContant.*;

/**
 * xx秒后执行自动更新任务
 * @author qgl
 * @date 2018/11/14
 */
public class DelayExeScheduleTask extends BaseTask{

    public DelayExeScheduleTask(ScheduledThreadPoolExecutor scheduledExecutor, IUrlContentService urlContentService, IOnlineNumberService onlineNumberService, IAllServerInfoService allServerInfoService) {
        this.scheduledExecutor = scheduledExecutor;
        this.urlContentService = urlContentService;
        this.onlineNumberService = onlineNumberService;
        this.allServerInfoService = allServerInfoService;
    }

    @Override
    public void run() {
        SaveUrlContentTask urlContentTask = new SaveUrlContentTask(urlContentService);
        urlContentTask.setThreadName(URLCONTENT_THREAD_NAME);
        SaveServerInfoTask serverInfoTask = new SaveServerInfoTask(onlineNumberService);
        serverInfoTask.setThreadName(SERVERINFO_THREAD_NAME);
        UpdateServerOpenDaysTask updateServerOpenDaysTask = new UpdateServerOpenDaysTask(allServerInfoService);
        updateServerOpenDaysTask.setThreadName(SERVER_OPEN_DAYS_THREAD_NAME);
        long now = System.currentTimeMillis();
        scheduledExecutor.scheduleAtFixedRate(urlContentTask, SAVE_URLCONTENT_TASK_INITIALDELAY, SAVE_URLCONTENT_PERIOD, TimeUnit.MILLISECONDS);
        scheduledExecutor.scheduleAtFixedRate(serverInfoTask, SAVE_SERVER_INFO_PERIOD, SAVE_SERVER_INFO_PERIOD, TimeUnit.MILLISECONDS);
        long updateServerOpenDayDelay = DateManager.getInstance().getUpdateServerOpenDaysTaskDelayTime(now, UPDATE_SERVER_OPEN_DAYS_TIME);
        scheduledExecutor.scheduleAtFixedRate(updateServerOpenDaysTask, updateServerOpenDayDelay, UPDATE_SERVER_OPEN_DAYS_PERIOD, TimeUnit.MILLISECONDS);
        logger.info(scheduledExecutor.getTaskCount() + " 个定时任务开启成功！ " + DateUtil.getRemainTime(SAVE_URLCONTENT_TASK_INITIALDELAY) + "后第一次执行自动更新网页内容任务， " +
                DateUtil.getRemainTime(SAVE_SERVER_INFO_PERIOD) + "后，第一次执行自动添加服务器在线人数信息任务， " + DateUtil.getRemainTime(updateServerOpenDayDelay) + "后第一次执行自动更新服务器开服天数任务");
    }
}
