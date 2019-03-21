package com.ledo.dao;

import com.ledo.beans.AllServerInfo;
import com.ledo.beans.Page;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface IAllServer {
    ArrayList<AllServerInfo> queryAllServerInfo();
    ArrayList<AllServerInfo> queryByCondition(AllServerInfo allServerInfo);
    /** 只更新Linux服务器的在线人数信息 */
    void updateOnlineNumbersInfo(@Param("onlineNum")int onlineNum, @Param("zoneId")int zoneId);
}
