package com.ledo.dao;

import com.ledo.beans.AllServerInfo;
import com.ledo.beans.RechargeInfo;
import com.ledo.beans.ServerHistoryInfo;

import java.util.ArrayList;

/**
 * 管理员CRUD权限 dao
 * @author qgl
 * @date 2018/10/24
 */
public interface IAdministrator {
    /** 插入服务器在线信息 */
    void insertServerInfo(ServerHistoryInfo serverInfo);
    /** 查询服务器最近25条历史在线信息 */
    ArrayList<ServerHistoryInfo> queryServerHistoryInfoByLimit25();
    /** 删除Linux服务器相关信息 */
    void deleteLinuxServerInfo();
    /** 插入Linux服务器相关信息 */
    void insertLinuxServerInfo(AllServerInfo allServerInfo);
    /** 删除充值数据 */
    void deleteRechargeInfo();
    /** 插入充值数据 */
    void insertRechargeInfo(RechargeInfo rechargeInfo);
}
