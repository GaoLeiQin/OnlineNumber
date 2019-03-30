package com.ledo.service;

import com.ledo.beans.AllServerInfo;

import java.util.ArrayList;

/**
 * Linux服务器 service 接口
 * @author qgl
 * @date 2018/10/24
 */
public interface IAllServerInfoService {
    /** 删除Linux服务器相关信息 */
    void deleteLinuxServerInfo();
    /** 更新Linux服务器全部信息 */
    void updateLinuxServerInfo();
    /** 查询Linux服务器相关信息 */
    ArrayList<AllServerInfo> referLinuxServerInfo();
    /** 只更新Linux服务器在线人数信息 */
    void onlyUpdateOnlineNumbersInfo();
    /** 只更新Linux服务器开服天数信息 */
    void onlyUpdateServerOpenDaysInfo();
    /** 查询所有服务器信息 */
    ArrayList<AllServerInfo> referAllServerInfo();
    /** 通过条件查询所有服务器信息 */
    ArrayList<AllServerInfo> referServerInfoByCondition(AllServerInfo serverInfo);

}