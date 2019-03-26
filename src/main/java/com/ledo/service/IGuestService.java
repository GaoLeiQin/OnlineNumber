package com.ledo.service;

import com.ledo.beans.GuestInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * 访客service 接口
 * @author qgl
 * @date 2018/11/16
 */
public interface IGuestService {
    /** 新增访客信息 */
    void addGuestInfo(HttpServletRequest request, String userName);
    /** 查询所有访客信息 */
    ArrayList<GuestInfo> queryGuestInfo();
    /** 根据条件查询访客信息 */
    ArrayList<GuestInfo> queryGuestInfoByCondition(GuestInfo guestInfo);
    /** 根据条件查询访客信息结果（处理分页时需要使用的数据）*/
    Integer queryGuestInfoCountByCondition(GuestInfo guestInfo);
}
