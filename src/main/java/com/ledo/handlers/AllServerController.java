package com.ledo.handlers;

import com.ledo.beans.AllServerInfo;
import com.ledo.service.IAllServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/** Linux服务器控制器
 * @author qgl
 * @date 2018/10/9
 */
@Controller
@RequestMapping("/allServer")
public class AllServerController {
    @Autowired
    @Qualifier("allServerInfoService")
    IAllServerInfoService allServerInfoService;

    @RequestMapping("/queryServerInfoByCondition.do")
    public ModelAndView queryByCondition(AllServerInfo serverInfo) {
        ModelAndView mv = new ModelAndView();
        ArrayList<AllServerInfo> serverInfosByCondition  = allServerInfoService.referServerInfoByCondition(serverInfo);
        mv.addObject("serverInfoByCondition", serverInfosByCondition);
        mv.addObject("serverInfo", serverInfo);
        mv.setViewName("allServerInfo");
        return mv;
    }

}
