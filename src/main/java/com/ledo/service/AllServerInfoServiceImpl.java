package com.ledo.service;

import com.ledo.beans.AllServerInfo;
import com.ledo.beans.UrlContent;
import com.ledo.dao.IAllServer;
import com.ledo.dao.IUrlContent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.ledo.common.FileConstant.RECHARGE_LOG_PATH;
import static com.ledo.common.FileConstant.ZONE_OPT_PATH;

/** 更新全部服务器信息
 * @author qgl
 * @date 2018/10/9
 */
@Service("allServerInfoService")
public class AllServerInfoServiceImpl implements IAllServerInfoService{
    Logger logger = Logger.getLogger(AllServerInfoServiceImpl.class);

    @Autowired
    @Qualifier("IAllServer")
    private IAllServer allServerDao;

    @Autowired
    @Qualifier("IUrlContent")
    private IUrlContent urlContentDao;

    @Override
    public ArrayList<AllServerInfo> referAllServerInfo() {
        return allServerDao.queryAllServerInfo();
    }

    @Override
    public ArrayList<AllServerInfo> referServerInfoByCondition(AllServerInfo serverInfo) {
        return allServerDao.queryByCondition(serverInfo);
    }

    @Override
    public void onlyUpdateOnlineNumbersInfo() {
        ArrayList<UrlContent> urlContents = urlContentDao.queryUrlContents();
        for (UrlContent urlContent : urlContents) {
            allServerDao.updateOnlineNumbersInfo(urlContent.getOnlineNum(), urlContent.getZoneId());
        }
    }
}
