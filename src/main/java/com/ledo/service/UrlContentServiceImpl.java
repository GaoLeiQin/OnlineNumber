package com.ledo.service;

import com.ledo.beans.UrlContent;
import com.ledo.dao.IUrlContent;
import com.ledo.manager.URLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ledo.common.URLConstant.*;
import static com.ledo.common.URLConstant.GAT_CHANNEL;

/**
 * 网页内容service 具体实现
 * @author qgl
 * @date 2018/10/24
 */
@Service("urlContentService")
public class UrlContentServiceImpl extends BaseService implements IUrlContentService{
    /** 对网页内容的 CRUD */
    @Autowired
    @Qualifier("IUrlContent")
    private IUrlContent urlContentDao;

    @Override
    public ArrayList<UrlContent> queryUrlContents() {
        ArrayList<UrlContent> contents = urlContentDao.queryUrlContents();
        if (contents.size() < ONLINE_SERVER_SUM) {
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

    @Override
    public void updateUrlContent() {
        urlContentDao.deleteUrlContent();
        boolean isGAT = false;
        for (Map.Entry<String, URL> urls : URLManager.getInstance().getUrl().entrySet()) {
            if (GAT.equals(urls.getKey())) {
                isGAT = true;
            }
            URL url = urls.getValue();
            for (UrlContent urlContent : URLManager.getInstance().getUrlContents(url)) {
                if (isGAT) {
                    urlContent.setChannel("GAT_" + urlContent.getChannel());
                }
                urlContentDao.insertUrlContent(urlContent);
            }
        }
    }

}
