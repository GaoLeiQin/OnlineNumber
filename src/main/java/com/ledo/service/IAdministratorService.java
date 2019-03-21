package com.ledo.service;

import com.ledo.beans.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * 管理员service 接口
 * @author qgl
 * @date 2018/10/24
 */
public interface IAdministratorService {
    /** 查询网页内容 */
    ArrayList<UrlContent> queryUrlContents();
    /** 更新网页内容 */
    void updateUrlContent();
    /** 新增服务器在线信息 */
    void addServerInfo();
    /** 查询服务器最近25条历史在线信息 */
    ArrayList<ServerHistoryInfo> referServerHistoryInfoByLimit25();
    /** 删除Linux服务器相关信息 */
    void deleteLinuxServerInfo();
    /** 更新Linux服务器相关信息 */
    void updateLinuxServerInfo();
    /** 查询Linux服务器相关信息 */
    ArrayList<AllServerInfo> referLinuxServerInfo();
    /** 删除充值数据 */
    void deleteRechargeInfo();
    /** 更新充值数据 */
    void updateRechargeInfo();
    /** 查询充值数据 */
    ArrayList<RechargeInfo> referRechargeInfo();

    /** 新增访客信息 */
    void addGuestInfo(HttpServletRequest request, String userName);
    /** 查询所有访客信息 */
    ArrayList<GuestInfo> queryGuestInfo();
    /** 根据条件查询访客信息 */
    ArrayList<GuestInfo> queryGuestInfoByCondition(GuestInfo guestInfo);
    /** 根据条件查询结果条数（处理分页时需要使用的数据）*/
    Integer queryGuestInfoCountByCondition(GuestInfo guestInfo);

    /** 开启自动更新任务 */
    void openAutoUpdateTask();
}
