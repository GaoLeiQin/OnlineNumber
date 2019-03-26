package com.ledo.task;

import com.ledo.beans.UrlContent;
import com.ledo.common.ThreadContant;
import com.ledo.dao.IUrlContent;
import com.ledo.manager.URLManager;

import java.net.URL;
import java.util.Map;

import static com.ledo.common.URLConstant.GAT;

/**
 * 保存网页内容定时线程
 * @author qgl
 * @date 2018/11/14
 */
public class SaveUrlContentTask extends Task{

    public SaveUrlContentTask(IUrlContent urlContentDao) {
        this.urlContentDao = urlContentDao;
    }

    @Override
    public void run() {
        this.updateUrlContent();
    }

    /**
     * 更新网页内容
     */
    public void updateUrlContent() {
        urlContentDao.deleteUrlContent();
        boolean isGAT = false;
        for (Map.Entry<String, URL> urls : URLManager.getInstance().getUrl().entrySet()) {
            if (GAT.equals(urls.getKey())) {
                isGAT = true;
            }
            for (UrlContent urlContent : URLManager.getInstance().getUrlContents(urls.getValue())) {
                if (isGAT) {
                    urlContent.setChannel("GAT_" + urlContent.getChannel());
                }
                urlContentDao.insertUrlContent(urlContent);
            }
        }

        logger.warn("QHSJSERVERINFO " + urlContentDao.queryUrlContents());
    }
}
