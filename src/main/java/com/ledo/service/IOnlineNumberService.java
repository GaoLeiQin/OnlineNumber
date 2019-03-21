package com.ledo.service;

import com.ledo.beans.Page;
import com.ledo.beans.ServerHistoryInfo;
import com.ledo.beans.UrlContent;
import com.ledo.beans.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

public interface IOnlineNumberService {
    /** 查询用户名是否存在 */
    Integer referUserNameCount(User user);
    /** 根据用户名查询用户的密码 */
    Integer verifyUserInfo(User user);

    /** 查询网页内容 */
    ArrayList<UrlContent> queryUrlContents();

    /** 查询服务器历史数据 */
    Integer referHistoryInfoCountByCondition(ServerHistoryInfo historyByCondition);
    ArrayList<ServerHistoryInfo> referHistoryInfoByCondition(ServerHistoryInfo historyByCondition);
    ArrayList<ServerHistoryInfo> referServerHistoryInfo();
    ArrayList<ServerHistoryInfo> referServerHistoryInfoByPage(Page page);

    /** 将网页内容分为 官服、硬核混服、安卓混服、港澳台四部分 */
    HashMap<String, ArrayList<UrlContent>> getURLContentsMapByCondition(ArrayList<UrlContent> urlContents);

    /** 新增访客信息 */
    void addGuestInfo(HttpServletRequest request, String userName);

}