package com.ledo.dao;

import com.ledo.beans.Page;
import com.ledo.beans.ServerHistoryInfo;

import java.util.ArrayList;
import java.util.HashMap;

public interface IOnlineNumber {
    /** 查询服务器的历史信息 */
    ArrayList<ServerHistoryInfo> queryServerHistoryInfo();
    /** 分页查询服务器的历史信息 */
    ArrayList<ServerHistoryInfo> queryServerHistoryInfoByPage(Page page);
    /** 根据条件查询服务器的历史信息（降序排列）*/
    ArrayList<ServerHistoryInfo> queryHistoryInfoByCondition(ServerHistoryInfo serverHistoryInfo);
    /** 根据条件查询结果条数（处理分页时需要使用的数据）*/
    Integer queryHistoryInfoCountByCondition(ServerHistoryInfo serverHistoryInfo);

}
