package com.ledo.handlers;

import com.ledo.beans.GuestInfo;
import com.ledo.beans.Page;
import com.ledo.beans.ServerHistoryInfo;
import com.ledo.beans.UrlContent;
import com.ledo.manager.PageManager;
import com.ledo.manager.URLManager;
import com.ledo.service.*;
import com.ledo.task.AllTask;
import com.ledo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static com.ledo.common.ServerConstant.*;

/**
 * 管理员页面 控制器
 * @author qgl
 * @date 2018/10/11
 */
@Controller
@RequestMapping("/admin")
public class AdministratorController {
    private String updateLinuxServerInfoDate;
    private String updateRechargeInfoDate;
    private boolean isOpenedTask;
    @Autowired
    @Qualifier("urlContentService")
    IUrlContentService urlContentService;

    @Autowired
    @Qualifier("onlineNumberService")
    IOnlineNumberService onlineNumberService;

    @Autowired
    @Qualifier("allServerInfoService")
    IAllServerInfoService allServerInfoService;

    @Autowired
    @Qualifier("guestService")
    IGuestService guestService;

    @Autowired
    @Qualifier("rechargeInfoService")
    IRechargeInfoService rechargeInfoService;

    @RequestMapping("/adminShow.do")
    public ModelAndView adminShow(boolean isUpdateLinuxServerInfo, String userName,
                                  boolean isUpdateRechargeInfo, HttpServletRequest request, boolean isNowStartTask){
        if (!URLManager.getInstance().canVisit(request)) {
            return new ModelAndView("adminlogin");
        }

        userName = userName == null ? OTHER_USER : userName;
        ModelAndView mv = new ModelAndView();
        int linuxServerSum = allServerInfoService.referLinuxServerInfo().size();
        if (isUpdateLinuxServerInfo) {
            userName = UPDATE_LINUX_OPERATION;
            allServerInfoService.deleteLinuxServerInfo();
            allServerInfoService.updateLinuxServerInfo();
            updateLinuxServerInfoDate = DateUtil.getNowFormatDate();
            mv.addObject("conflictSum", urlContentService.queryUrlContents().size() - linuxServerSum);
        }

        if (isUpdateRechargeInfo) {
            userName = UPDATE_RECHARGE_OPERATION;
            rechargeInfoService.deleteRechargeInfo();
            rechargeInfoService.updateRechargeInfo();
            updateRechargeInfoDate = DateUtil.getNowFormatDate();

        }

        if (isNowStartTask && !isOpenedTask) {
            isOpenedTask = true;
            userName = START_TASK_OPERATION;
            AllTask.getInstance().startAutoUpdateTask(urlContentService, onlineNumberService, allServerInfoService);
            AllTask.getInstance().executeMonitorThreadTask(urlContentService, onlineNumberService, allServerInfoService);
        }

        guestService.addGuestInfo(request, userName);
        mv.addObject("updateLinuxServerInfoDate", updateLinuxServerInfoDate);
        mv.addObject("updateRechargeInfoDate", updateRechargeInfoDate);
        mv.addObject("rechargeSum", rechargeInfoService.referRechargeInfo().size());
        mv.addObject("linuxServerSum", linuxServerSum);
        mv.addObject("isOpenedTask", isOpenedTask);
        mv.setViewName("adminShow");
        return mv;
    }

    @RequestMapping("/guestInfo.do")
    public ModelAndView guestInfo(Page page, GuestInfo guest) {
        ModelAndView mv = new ModelAndView();
        Page pageInfo = PageManager.getInstance().setPageInfo(page, guestService.queryGuestInfoCountByCondition(guest));
        guest.setPage(pageInfo);
        ArrayList<GuestInfo> guestInfos = guestService.queryGuestInfoByCondition(guest);

        mv.addObject("guest", guest);
        mv.addObject("page", pageInfo);
        mv.addObject("guestInfos", guestInfos);
        mv.setViewName("guestInfo");
        return mv;
    }

    @RequestMapping("/autoUpdateUrlContent.do")
    public ModelAndView AutoUpdateUrlContent(){
        ModelAndView mv = new ModelAndView();
        urlContentService.updateUrlContent();
        ArrayList<UrlContent> serverInfos = urlContentService.queryUrlContents();
        mv.addObject("serverInfos", serverInfos);
        mv.setViewName("autoUpdateUrlContent");
        return mv;
    }

    @RequestMapping("/autoInsertOnlineServerInfo.do")
    public ModelAndView AutoInsertOnlineServerInfo(){
        ModelAndView mv = new ModelAndView();
        onlineNumberService.addServerInfo();
        ArrayList<ServerHistoryInfo> serverHistoryInfoByLimit = onlineNumberService.referServerHistoryInfoByLimit25();
        mv.addObject("serverHistoryInfoByLimit", serverHistoryInfoByLimit);
        mv.setViewName("autoInsertOnlineServerInfo");
        return mv;
    }

}
