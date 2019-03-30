package com.ledo.task;

import com.ledo.service.IAllServerInfoService;

/**
 * 更新Linux服务器信息定时线程
 * @author qgl
 * @date 2018/11/18
 */
public class UpdateServerOpenDaysTask extends BaseTask{

    public UpdateServerOpenDaysTask(IAllServerInfoService allServerInfoService) {
        this.allServerInfoService = allServerInfoService;
    }

    @Override
    public void run() {
        this.allServerInfoService.onlyUpdateServerOpenDaysInfo();
        logger.info("更新服务器开服天数成功！");
    }
}
