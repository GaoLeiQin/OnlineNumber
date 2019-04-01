package com.ledo.dao;

import com.ledo.beans.AllServerInfo;
import com.ledo.beans.Page;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface IAllServer {
    /** 删除Linux服务器相关信息 */
    void deleteLinuxServerInfo();
    /** 插入Linux服务器相关信息 */
    void insertLinuxServerInfo(AllServerInfo allServerInfo);
    /** 查询所有Linux服务器信息 */
    ArrayList<AllServerInfo> queryAllServerInfo();
    /** 根据条件查询Linux服务器信息 */
    ArrayList<AllServerInfo> queryByCondition(AllServerInfo allServerInfo);
    /** 只更新Linux服务器的开服天数信息 */
    void updateServerOpenDaysInfo(@Param("openTime")String openTime, @Param("openDays")int openDays);
}
