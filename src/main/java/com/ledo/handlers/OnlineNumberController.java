package com.ledo.handlers;

import com.ledo.beans.Page;
import com.ledo.beans.ServerHistoryInfo;
import com.ledo.beans.UrlContent;
import com.ledo.manager.PageManager;
import com.ledo.manager.URLManager;
import com.ledo.service.IGuestService;
import com.ledo.service.IOnlineNumberService;
import com.ledo.service.IUrlContentService;
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
public class OnlineNumberController {
    @Autowired
    @Qualifier("onlineNumberService")
    IOnlineNumberService onlineNumberService;

    @Autowired
    @Qualifier("guestService")
    IGuestService guestService;

    @Autowired
    @Qualifier("urlContentService")
    IUrlContentService urlContentService;

    @RequestMapping("/guest/guestShow.do")
    public ModelAndView show(HttpServletRequest request, String userName){
        if (!URLManager.getInstance().canVisit(request)) {
            return new ModelAndView("login");
        }
        guestService.addGuestInfo(request, userName);
        ModelAndView mv = new ModelAndView();
        ArrayList<UrlContent> allUrlContents = urlContentService.queryUrlContents();
        HashMap<String, ArrayList<UrlContent>> urlContentsMapByCondition = urlContentService.getURLContentsMapByCondition(allUrlContents);
        ArrayList<UrlContent> officialContents = new ArrayList<>();
        ArrayList<UrlContent> androidContents = new ArrayList<>();
        ArrayList<UrlContent> hardAllianceContents = new ArrayList<>();
        ArrayList<UrlContent> gatContents = new ArrayList<>();
        URLManager.getInstance().setContentsByCondition(urlContentsMapByCondition, officialContents, androidContents, hardAllianceContents, gatContents);
        URLManager.getInstance().sortAllUrlContensByOnlineNum(allUrlContents);
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
        pageInfo = PageManager.getInstance().setPageInfo(page, onlineNumberService.referHistoryInfoCountByCondition(history));
        history.setPage(pageInfo);
        ArrayList<ServerHistoryInfo> historyInfos = onlineNumberService.referHistoryInfoByCondition(history);

        mv.addObject("historyInfo", history);
        mv.addObject("page", pageInfo);
        mv.addObject("serverHistoryInfos", historyInfos);
        mv.setViewName("history");

        return mv;
    }

}
