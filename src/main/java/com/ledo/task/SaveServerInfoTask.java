package com.ledo.task;

import com.ledo.beans.ServerHistoryInfo;
import com.ledo.dao.IOnlineNumber;
import com.ledo.dao.IUrlContent;
import com.ledo.manager.FileManager;
import com.ledo.util.DateUtil;

/**
 * 保存服务器信息定时线程
 * @author qgl
 * @date 2018/11/14
 */
public class SaveServerInfoTask extends Task {

    public SaveServerInfoTask(IOnlineNumber onlineNumberDao, IUrlContent urlContentDao) {
        this.onlineNumberDao = onlineNumberDao;
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
        ServerHistoryInfo server = new ServerHistoryInfo(DateUtil.getNowFormatDate(), urlContentDao.queryOfficialSum(), urlContentDao.queryMixSum(),
                urlContentDao.queryGatSum(), urlContentDao.queryAllSum());

        boolean isUrlContentNull = urlContentDao.queryOfficialSum() == null || urlContentDao.queryMixSum() == null ||
                urlContentDao.queryGatSum() == null || urlContentDao.queryAllSum() == null;
        if (isUrlContentNull) {
            logger.error(" &$& 删除数据库，网页访问为空的数据：" + server);
        }
        onlineNumberDao.insertServerInfo(server);
    }

}
