package com.ledo.handlers;

import com.ledo.beans.Page;
import com.ledo.beans.RechargeInfo;
import com.ledo.service.IRechargeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/recharge")
public class RechargeController {
    @Autowired
    @Qualifier("rechargeInfoService")
    IRechargeInfoService rechargeInfoService;

    @RequestMapping("/rechargeInfo.do")
    public ModelAndView showRechargeInfo(Page page) {
        int pageSize = 25;
        page.setPageSize(pageSize);
        Integer currentPage = page.getCurrentPage();

        if (currentPage == null || currentPage <= 0) {
            currentPage=1;
            page.setCurrentPage(currentPage);
        }
        int startRow = currentPage == 1 ? 0 : (currentPage-1)*pageSize;
        page.setStartRow(startRow);

        ModelAndView mv = new ModelAndView();
        ArrayList<RechargeInfo> rechargeInfos = rechargeInfoService.referRechargeInfoByPage(page);

        int totalCounts = rechargeInfoService.referRechargeInfo().size();
        int totalPages = (totalCounts % pageSize == 0) ? (totalCounts/pageSize):(totalCounts/pageSize+1);//总页数=总条数/页大小+1
        page.setTotalPage(totalPages);//总页数
        page.setTotalRows(totalCounts);//总行数

        mv.addObject("page", page);
        mv.addObject("rechargeInfos", rechargeInfos);
        mv.setViewName("rechargeInfo");
        return mv;
    }

}
