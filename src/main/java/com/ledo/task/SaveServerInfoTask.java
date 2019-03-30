package com.ledo.task;

import com.ledo.service.IOnlineNumberService;

/**
 * 保存服务器信息定时线程
 * @author qgl
 * @date 2018/11/14
 */
public class SaveServerInfoTask extends BaseTask {

    public SaveServerInfoTask(IOnlineNumberService onlineNumberService) {
        this.onlineNumberService = onlineNumberService;
    }

    @Override
    public void run() {
        this.onlineNumberService.addServerInfo();
    }

}
