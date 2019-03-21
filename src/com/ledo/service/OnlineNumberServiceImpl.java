package com.ledo.service;

import com.ledo.beans.Page;
import com.ledo.beans.ServerHistoryInfo;
import com.ledo.beans.UrlContent;
import com.ledo.beans.User;
import com.ledo.common.URLManager;
import com.ledo.dao.IGuest;
import com.ledo.dao.IOnlineNumber;
import com.ledo.dao.IUrlContent;
import com.ledo.dao.IUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

import static com.ledo.common.ServerConstant.*;

/** 查询网页内容后分类显示
 * @author qgl
 * @date 2018/9/24
 */
@Service("onlineNumberService")
public class OnlineNumberServiceImpl implements IOnlineNumberService{
    Logger logger = Logger.getLogger(OnlineNumberServiceImpl.class);

    /** 对网页内容的 CRUD */
    @Autowired
    @Qualifier("IUrlContent")
    private IUrlContent urlContentDao;

    @Autowired
    @Qualifier("IGuest")
    private IGuest guestDao;

    @Autowired
    @Qualifier("IUser")
    private IUser userDao;

    @Override
    public Integer referUserNameCount(User user) {
        return userDao.queryUserNameCount(user);
    }

    @Override
    public Integer verifyUserInfo(User user) {
        return userDao.verifyUser(user);
    }

    @Override
    public void addGuestInfo(HttpServletRequest request, String userName) {
        guestDao.insertGuestInfo(URLManager.increaseGuestInfo(request, userName));
    }

    @Override
    public ArrayList<UrlContent> queryUrlContents() {
        ArrayList<UrlContent> contents = urlContentDao.queryUrlContents();
        if (contents.size() < URLManager.ONLINE_SERVER_SUM) {
            // 等待数据更新
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            contents = urlContentDao.queryUrlContents();
        }
        return contents;
    }

    /** 对网页内容进行处理后保存相关数据 */
    @Autowired
    @Qualifier("IOnlineNumber")
    private IOnlineNumber onlineNumberDao;

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
    public HashMap<String, ArrayList<UrlContent>> getURLContentsMapByCondition(ArrayList<UrlContent> urlContents) {
        HashMap<String, ArrayList<UrlContent>> urlContentsMapByCondition = new HashMap<>();
        ArrayList<UrlContent> officialContents = new ArrayList<>();
        ArrayList<UrlContent> androidContents = new ArrayList<>();
        ArrayList<UrlContent> hardAllianceContents = new ArrayList<>();
        ArrayList<UrlContent> gatContents = new ArrayList<>();
        for (UrlContent urlContent : urlContents) {
            String channel = urlContent.getChannel().trim();
            if (channel.equals(MIX_ANDROID_CHANNEL)) {
                androidContents.add(urlContent);
            }else if (channel.equals(MIX_HARDALLIANCE_CHANNEL)) {
                hardAllianceContents.add(urlContent);
            }else if (channel.equals(GAT_CHANNEL)) {
                gatContents.add(urlContent);
            }else if (channel.equals(OFFICIAL_CHANNEL)){
                officialContents.add(urlContent);
            }else {
                logger.error(" ****** 未知渠道 ******");
            }
        }

        urlContentsMapByCondition.put(OFFICIAL_CHANNEL, officialContents);
        urlContentsMapByCondition.put(MIX_ANDROID_CHANNEL, androidContents);
        urlContentsMapByCondition.put(MIX_HARDALLIANCE_CHANNEL, hardAllianceContents);
        urlContentsMapByCondition.put(GAT_CHANNEL, gatContents);

        return urlContentsMapByCondition;
    }

}
