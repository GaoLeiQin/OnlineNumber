package com.ledo.service;

import com.ledo.beans.ServerHistoryInfo;
import com.ledo.dao.IOnlineNumber;
import com.ledo.dao.IUrlContent;
import com.ledo.task.AllTask;
import com.ledo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 管理员service 具体实现
 * @author qgl
 * @date 2018/10/24
 */
@Service("administratorService")
public class AdministratorServiceImpl extends BaseService implements IAdministratorService{
    @Autowired
    @Qualifier("IUrlContent")
    private IUrlContent urlContentDao;

    @Autowired
    @Qualifier("IOnlineNumber")
    private IOnlineNumber onlineNumberDao;

    @Override
    public void openAutoUpdateTask() {
        AllTask.getInstance().startAutoUpdateTask(onlineNumberDao, urlContentDao);
        AllTask.getInstance().executeMonitorThreadTask(onlineNumberDao, urlContentDao);

    }

    @Override
    public void addServerInfo() {
        ServerHistoryInfo server = new ServerHistoryInfo(DateUtil.getNowFormatDate(), urlContentDao.queryOfficialSum(), urlContentDao.queryMixSum(),
                urlContentDao.queryGatSum(), urlContentDao.queryAllSum());
        onlineNumberDao.insertServerInfo(server);
    }

}
