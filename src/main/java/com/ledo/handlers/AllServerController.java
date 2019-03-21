package com.ledo.handlers;

import com.ledo.beans.AllServerInfo;
import com.ledo.common.FileManager;
import com.ledo.service.IAllServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/allServer")
public class AllServerController {
    private String updateTime;
    @Autowired
    @Qualifier("allServerInfoService")
    IAllServerInfoService allServerInfoService;

    @RequestMapping("/queryServerInfoByCondition.do")
    public ModelAndView queryByCondition(AllServerInfo serverInfo, boolean isUpdate) {
        ModelAndView mv = new ModelAndView();

        if (isUpdate) {
            allServerInfoService.onlyUpdateOnlineNumbersInfo();
            updateTime = FileManager.getNowFormatDate();
        }

        ArrayList<AllServerInfo> serverInfosByCondition  = allServerInfoService.referServerInfoByCondition(serverInfo);
        mv.addObject("serverInfoByCondition", serverInfosByCondition);
        mv.addObject("serverInfo", serverInfo);
        mv.addObject("updateTime", updateTime);
        mv.setViewName("allServerInfo");
        return mv;
    }

    @RequestMapping("/test.do")
    public ModelAndView test() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("chart");
        return mv;
    }

}
