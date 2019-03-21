package com.ledo.handlers;

import com.ledo.beans.AllServerInfo;
import com.ledo.service.IAllServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/allServer")
public class AllServerController {
    @Autowired
    @Qualifier("allServerInfoService")
    IAllServerInfoService allServerInfoService;

    @RequestMapping("/queryServerInfoByCondition.do")
    public ModelAndView queryByCondition(AllServerInfo serverInfo, boolean isUpdate, String date) {
        ModelAndView mv = new ModelAndView();

        if (isUpdate) {
            allServerInfoService.onlyUpdateOnlineNumbersInfo();
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        }

        ArrayList<AllServerInfo> serverInfosByCondition  = allServerInfoService.referServerInfoByCondition(serverInfo);
        mv.addObject("serverInfoByCondition", serverInfosByCondition);
        mv.addObject("serverInfo", serverInfo);
        mv.addObject("date", date);
        mv.setViewName("allServerInfo");
        return mv;
    }

    @RequestMapping("/test.do")
    public ModelAndView test() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("chart");
        return mv;
    }

    @RequestMapping("/ajax.do")
    public @ResponseBody List ajax() {
        List<Integer> result = new ArrayList<>();
        result.add(1);
        result.add(3);
        result.add(5);
        result.add(7);
        result.add(9);
        return result;
    }

}
