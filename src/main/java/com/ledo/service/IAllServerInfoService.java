package com.ledo.service;

import com.ledo.beans.AllServerInfo;
import com.ledo.beans.Page;

import java.util.ArrayList;

/**
 * Linux服务器 service 接口
 * @author qgl
 * @date 2018/10/24
 */
public interface IAllServerInfoService {
    /** 删除Linux服务器相关信息 */
    void deleteLinuxServerInfo();
    /** 更新Linux服务器相关信息 */
    void updateLinuxServerInfo();
    /** 查询Linux服务器相关信息 */
    ArrayList<AllServerInfo> referLinuxServerInfo();
    void onlyUpdateOnlineNumbersInfo();
    ArrayList<AllServerInfo> referAllServerInfo();
    ArrayList<AllServerInfo> referServerInfoByCondition(AllServerInfo serverInfo);

}