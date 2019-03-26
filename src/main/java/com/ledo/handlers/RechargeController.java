package com.ledo.handlers;

import com.ledo.beans.Page;
import com.ledo.beans.RechargeInfo;
import com.ledo.manager.PageManager;
import com.ledo.service.IRechargeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/** 充值信息控制器
 * @author qgl
 * @date 2018/10/9
 */
@Controller
@RequestMapping("/recharge")
public class RechargeController {
    @Autowired
    @Qualifier("rechargeInfoService")
    IRechargeInfoService rechargeInfoService;

    @RequestMapping("/rechargeInfo.do")
    public ModelAndView showRechargeInfo(Page page, RechargeInfo rechargeInfo) {
        ModelAndView mv = new ModelAndView();
        Page pageInfo = null;
        pageInfo = PageManager.getInstance().setPageInfo(page, rechargeInfoService.referRechargeInfoCountByCondition(rechargeInfo));
        rechargeInfo.setPage(pageInfo);
        ArrayList<RechargeInfo> rechargeInfoList = rechargeInfoService.referRechargeInfoByPage(rechargeInfo);

        mv.addObject("rechargeInfo", rechargeInfo);
        mv.addObject("page", pageInfo);
        mv.addObject("rechargeInfos", rechargeInfoList);
        mv.setViewName("rechargeInfo");
        return mv;
    }

}
