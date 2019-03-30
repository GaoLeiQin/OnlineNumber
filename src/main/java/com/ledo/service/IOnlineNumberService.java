package com.ledo.service;

import com.ledo.beans.Page;
import com.ledo.beans.ServerHistoryInfo;

import java.util.ArrayList;

/**
 * @author admin
 */
public interface IOnlineNumberService {
    /** 新增服务器在线信息 */
    void addServerInfo();
    /** 查询服务器历史数据 */
    Integer referHistoryInfoCountByCondition(ServerHistoryInfo historyByCondition);
    /** 查询服务器最近25条历史在线信息 */
    ArrayList<ServerHistoryInfo> referServerHistoryInfoByLimit25();
    /** 根据条件查询服务器历史在线信息 */
    ArrayList<ServerHistoryInfo> referHistoryInfoByCondition(ServerHistoryInfo historyByCondition);
    ArrayList<ServerHistoryInfo> referServerHistoryInfo();
    ArrayList<ServerHistoryInfo> referServerHistoryInfoByPage(Page page);

}