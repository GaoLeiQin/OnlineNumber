package com.ledo.handlers;

import com.ledo.beans.Page;
import com.ledo.beans.ServerHistoryInfo;
import com.ledo.beans.UrlContent;
import com.ledo.common.FileManager;
import com.ledo.common.Task;
import com.ledo.common.URLManager;
import com.ledo.service.IOnlineNumberService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 访客主页面控制器
 * @author qgl
 * @date 2018/10/11
 */
@Controller
public class GuestController {
    @Autowired
    @Qualifier("onlineNumberService")
    IOnlineNumberService onlineNumberService;

    @RequestMapping("/guest/guestShow.do")
    public ModelAndView show(HttpServletRequest request, String userName){
        // 若没有经过登录界面，则返回登录界面
        if (request.getHeader(URLManager.HEADER_PARAM_REFERER) == null) {
            return new ModelAndView("login");
        }
        onlineNumberService.addGuestInfo(request, userName);
        ModelAndView mv = new ModelAndView();
        ArrayList<UrlContent> allUrlContents = onlineNumberService.queryUrlContents();
        HashMap<String, ArrayList<UrlContent>> urlContentsMapByCondition = onlineNumberService.getURLContentsMapByCondition(allUrlContents);
        ArrayList<UrlContent> officialContents = new ArrayList<>();
        ArrayList<UrlContent> androidContents = new ArrayList<>();
        ArrayList<UrlContent> hardAllianceContents = new ArrayList<>();
        ArrayList<UrlContent> gatContents = new ArrayList<>();
        URLManager.setContentsByCondition(urlContentsMapByCondition, officialContents, androidContents, hardAllianceContents, gatContents);
        URLManager.sortAllUrlContensByOnlineNum(allUrlContents);
        mv.addObject("officialContents", officialContents);
        mv.addObject("androidContents", androidContents);
        mv.addObject("hardAllianceContents", hardAllianceContents);
        mv.addObject("gatContents", gatContents);
        mv.addObject("allUrlContents", allUrlContents);
        mv.addObject("onlineServerSum", allUrlContents.size());
        mv.setViewName("serverShow");
        return mv;
    }

    @RequestMapping("/guest/historyByCondition.do")
    public ModelAndView historyData(Page page, ServerHistoryInfo history) {
        ModelAndView mv = new ModelAndView();
        Page pageInfo = null;
        pageInfo = URLManager.setPageInfo(page, onlineNumberService.referHistoryInfoCountByCondition(history));
        history.setPage(pageInfo);
        ArrayList<ServerHistoryInfo> historyInfos = onlineNumberService.referHistoryInfoByCondition(history);

        mv.addObject("historyInfo", history);
        mv.addObject("page", pageInfo);
        mv.addObject("serverHistoryInfos", historyInfos);
        mv.setViewName("history");

//        Task task = new Task(4);
//        task.startScheduleRateTask(new SaveServerInfoTask(5));
//        task.startScheduleDelayTask(new SaveUrlContentTask(8));

        return mv;
    }

    public class SaveServerInfoTask implements Runnable {
        private int count;

        public SaveServerInfoTask(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            Logger.getLogger(GuestController.class).info(" 保存服务器信息！" + count);
        }
    }

    public class SaveUrlContentTask implements Runnable {
        private int count;

        public SaveUrlContentTask(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            System.out.println(FileManager.getNowFormatDate() + " 存储网页内容：" + count);
        }
    }

}
