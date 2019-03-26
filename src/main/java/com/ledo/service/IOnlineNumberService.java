package com.ledo.service;

import com.ledo.beans.Page;
import com.ledo.beans.ServerHistoryInfo;
import com.ledo.beans.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * @author admin
 */
public interface IOnlineNumberService {
    /** 查询服务器历史数据 */
    Integer referHistoryInfoCountByCondition(ServerHistoryInfo historyByCondition);
    /** 查询服务器最近25条历史在线信息 */
    ArrayList<ServerHistoryInfo> referServerHistoryInfoByLimit25();
    ArrayList<ServerHistoryInfo> referHistoryInfoByCondition(ServerHistoryInfo historyByCondition);
    ArrayList<ServerHistoryInfo> referServerHistoryInfo();
    ArrayList<ServerHistoryInfo> referServerHistoryInfoByPage(Page page);
}