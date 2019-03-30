package com.ledo.task;

import com.ledo.service.IUrlContentService;

/**
 * 保存网页内容定时线程
 * @author qgl
 * @date 2018/11/14
 */
public class SaveUrlContentTask extends BaseTask{

    public SaveUrlContentTask(IUrlContentService urlContentService) {
        this.urlContentService = urlContentService;
    }

    @Override
    public void run() {
        this.urlContentService.updateUrlContent();
    }
}
