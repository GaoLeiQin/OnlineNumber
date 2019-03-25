package com.ledo.handlers;

import com.ledo.beans.GuestInfo;
import com.ledo.beans.Page;
import com.ledo.beans.ServerHistoryInfo;
import com.ledo.beans.UrlContent;
import com.ledo.manager.FileManager;
import com.ledo.manager.URLManager;
import com.ledo.service.IAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

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
    @Qualifier("IAdministratorService")
    IAdministratorService administratorService;

    @RequestMapping("/adminShow.do")
    public ModelAndView adminShow(boolean isUpdateLinuxServerInfo, String userName,
                                  boolean isUpdateRechargeInfo, HttpServletRequest request, boolean isNowStartTask){
        // 若没有经过登录界面，则返回管理员登录界面
        if (request.getHeader(URLManager.HEADER_PARAM_REFERER) == null) {
            return new ModelAndView("adminlogin");
        }

        userName = userName == null ? "Other" : userName;
        ModelAndView mv = new ModelAndView();
        int linuxServerSum = administratorService.referLinuxServerInfo().size();
        if (isUpdateLinuxServerInfo) {
            userName = "UpdateLinux";
            administratorService.deleteLinuxServerInfo();
            administratorService.updateLinuxServerInfo();
            updateLinuxServerInfoDate = FileManager.getNowFormatDate();
            mv.addObject("conflictSum", administratorService.queryUrlContents().size() - linuxServerSum);
        }

        if (isUpdateRechargeInfo) {
            userName = "UpdateRecharge";
            administratorService.deleteRechargeInfo();
            administratorService.updateRechargeInfo();
            updateRechargeInfoDate = FileManager.getNowFormatDate();

        }

        if (isNowStartTask && !isOpenedTask) {
            isOpenedTask = true;
            userName = "StartTask";
            administratorService.openAutoUpdateTask();
        }

        administratorService.addGuestInfo(request, userName);
        mv.addObject("updateLinuxServerInfoDate", updateLinuxServerInfoDate);
        mv.addObject("updateRechargeInfoDate", updateRechargeInfoDate);
        mv.addObject("rechargeSum", administratorService.referRechargeInfo().size());
        mv.addObject("linuxServerSum", linuxServerSum);
        mv.addObject("isOpenedTask", isOpenedTask);
        mv.setViewName("adminShow");
        return mv;
    }

    @RequestMapping("/guestInfo.do")
    public ModelAndView guestInfo(Page page, GuestInfo guest) {
        ModelAndView mv = new ModelAndView();
        Page pageInfo = URLManager.setPageInfo(page, administratorService.queryGuestInfoCountByCondition(guest));
        guest.setPage(pageInfo);
        ArrayList<GuestInfo> guestInfos = administratorService.queryGuestInfoByCondition(guest);

        mv.addObject("guest", guest);
        mv.addObject("page", pageInfo);
        mv.addObject("guestInfos", guestInfos);
        mv.setViewName("guestInfo");
        return mv;
    }

    @RequestMapping("/autoUpdateUrlContent.do")
    public ModelAndView AutoUpdateUrlContent(){
        ModelAndView mv = new ModelAndView();
        administratorService.updateUrlContent();
        ArrayList<UrlContent> serverInfos = administratorService.queryUrlContents();
        mv.addObject("serverInfos", serverInfos);
        mv.setViewName("autoUpdateUrlContent");
        return mv;
    }

    @RequestMapping("/autoInsertOnlineServerInfo.do")
    public ModelAndView AutoInsertOnlineServerInfo(){
        ModelAndView mv = new ModelAndView();
        administratorService.addServerInfo();
        ArrayList<ServerHistoryInfo> serverHistoryInfoByLimit = administratorService.referServerHistoryInfoByLimit25();
        mv.addObject("serverHistoryInfoByLimit", serverHistoryInfoByLimit);
        mv.setViewName("autoInsertOnlineServerInfo");
        return mv;
    }
}
