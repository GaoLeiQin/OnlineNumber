package com.ledo.service;

import com.ledo.beans.Page;
import com.ledo.beans.ServerHistoryInfo;
import com.ledo.dao.IOnlineNumber;
import com.ledo.dao.IUrlContent;
import com.ledo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/** 查询网页内容后分类显示
 * @author qgl
 * @date 2018/9/24
 */
@Service("onlineNumberService")
public class OnlineNumberServiceImpl extends BaseService implements IOnlineNumberService{
    @Autowired
    @Qualifier("IUrlContent")
    private IUrlContent urlContentDao;


    @Autowired
    @Qualifier("IOnlineNumber")
    private IOnlineNumber onlineNumberDao;


    @Override
    public ArrayList<ServerHistoryInfo> referServerHistoryInfoByLimit25() {
        return onlineNumberDao.queryServerHistoryInfoByLimit25();
    }

    @Override
    public ArrayList<ServerHistoryInfo> referServerHistoryInfo() {
        return onlineNumberDao.queryServerHistoryInfo();
    }

    @Override
    public ArrayList<ServerHistoryInfo> referServerHistoryInfoByPage(Page page) {
        return onlineNumberDao.queryServerHistoryInfoByPage(page);
    }

    @Override
    public Integer referHistoryInfoCountByCondition(ServerHistoryInfo historyByCondition) {
        return onlineNumberDao.queryHistoryInfoCountByCondition(historyByCondition);
    }

    @Override
    public ArrayList<ServerHistoryInfo> referHistoryInfoByCondition(ServerHistoryInfo historyByCondition) {
        return onlineNumberDao.queryHistoryInfoByCondition(historyByCondition);
    }

    @Override
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
