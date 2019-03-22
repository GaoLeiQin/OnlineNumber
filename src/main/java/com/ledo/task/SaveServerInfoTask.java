package com.ledo.task;

import com.ledo.beans.ServerHistoryInfo;
import com.ledo.dao.IAdministrator;
import com.ledo.dao.IUrlContent;
import com.ledo.manager.FileManager;

/**
 * 保存服务器信息定时线程
 * @author qgl
 * @date 2018/11/14
 */
public class SaveServerInfoTask extends Task {

    public SaveServerInfoTask(IAdministrator administratorDao, IUrlContent urlContentDao) {
        this.administratorDao = administratorDao;
        this.urlContentDao = urlContentDao;
    }

    @Override
    public void run() {
        this.addServerInfo();
    }

    /**
     * 保存最新的服务器在线信息
     */
    public void addServerInfo() {
        ServerHistoryInfo server = new ServerHistoryInfo(FileManager.getNowFormatDate(), urlContentDao.queryOfficialSum(), urlContentDao.queryMixSum(),
                urlContentDao.queryGatSum(), urlContentDao.queryAllSum());

        boolean isNull = urlContentDao.queryOfficialSum() == null || urlContentDao.queryMixSum() == null ||
                urlContentDao.queryGatSum() == null || urlContentDao.queryAllSum() == null;
        if (isNull) {
            logger.error(" &$& 删除数据库，网页访问为空的数据：" + server);
        }
        administratorDao.insertServerInfo(server);
    }

    @Override
    public void setThreadName(String threadName) {
        this.setName(threadName);
    }

}
