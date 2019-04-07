package com.ledo.service;

import com.ledo.beans.ServerHistoryInfo;
import com.ledo.beans.UrlContent;
import com.ledo.cache.ServerInfoCache;
import com.ledo.cache.UrlCache;
import com.ledo.cache.UrlCache.CacheUrlContent;
import com.ledo.dao.IUrlContent;
import com.ledo.manager.URLManager;
import com.ledo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ledo.common.ThreadContant.SAVE_URLCONTENT_PERIOD;
import static com.ledo.common.URLConstant.*;

/**
 * 网页内容service 具体实现
 * @author qgl
 * @date 2018/10/24
 */
@Service("urlContentService")
public class UrlContentServiceImpl extends BaseService implements IUrlContentService{
    @Autowired
    @Qualifier("IUrlContent")
    private IUrlContent urlContentDao;

    @Override
    public List<UrlContent> queryUrlContents() {
        CacheUrlContent cacheUrlContent = UrlCache.getInstance().getCacheUrlContent();
        List<UrlContent> contents = cacheUrlContent.getAllUrlContents();
        if (contents == null || DateUtil.getIntervalTime(cacheUrlContent.getTimestamp(), System.currentTimeMillis()) > SAVE_URLCONTENT_PERIOD) {
            logger.error(" 缓存过期，已过期的网页内容缓存时间是 " + DateUtil.getFormatDateByMillSecond(cacheUrlContent.getTimestamp()));
            contents = urlContentDao.queryUrlContents();
        }
        if (contents.size() < ONLINE_SERVER_SUM) {
            // 等待数据更新
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            contents = urlContentDao.queryUrlContents();
        }
        return contents;
    }

    @Override
    public HashMap<String, ArrayList<UrlContent>> getURLContentsMapByCondition(List<UrlContent> urlContents) {
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
        List<UrlContent> allUrlContents = new ArrayList<>();
        for (Map.Entry<String, URL> urls : URLManager.getInstance().getUrl().entrySet()) {
            if (GAT.equals(urls.getKey())) {
                isGAT = true;
            }
            URL url = urls.getValue();
            List<UrlContent> urlContents = URLManager.getInstance().getUrlContents(url);
            allUrlContents.addAll(urlContents);
            for (UrlContent urlContent : urlContents) {
                if (isGAT) {
                    urlContent.setChannel("GAT_" + urlContent.getChannel());
                }
                urlContentDao.insertUrlContent(urlContent);
            }
        }
        logger.warn("QHSJSERVERINFO " + urlContentDao.queryUrlContents());
        this.updateCache(allUrlContents);
    }

    /**
     * 更新缓存信息
     * @param allUrlContents
     */
    private void updateCache(List<UrlContent> allUrlContents) {
        UrlCache.getInstance().setCacheUrlContent(UrlCache.getInstance().new CacheUrlContent(System.currentTimeMillis(), allUrlContents));

        Integer officialSum = urlContentDao.queryOfficialSum();
        Integer mixSum = urlContentDao.queryMixSum();
        Integer gatSum = urlContentDao.queryGatSum();
        Integer allSum = urlContentDao.queryAllSum();
        if (officialSum == null || mixSum == null || gatSum == null || allSum == null) {
            logger.error(" $$$ 404 Not Found 网页访问出错，出错网页内容：" + allUrlContents);
        } else {
            ServerInfoCache.getInstance().setServerHistoryInfo(new ServerHistoryInfo(DateUtil.getNowFormatDate(), officialSum, mixSum, gatSum, allSum));
        }
    }

}
