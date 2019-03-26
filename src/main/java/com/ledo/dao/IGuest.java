package com.ledo.dao;

import com.ledo.beans.GuestInfo;

import java.util.ArrayList;

public interface IGuest {
    /** 插入访客信息 */
    void insertGuestInfo(GuestInfo urlcontent_info);
    /** 查询所有访客信息 */
    ArrayList<GuestInfo> queryGuestInfo();
    /** 根据条件查询访客信息 */
    ArrayList<GuestInfo> queryGuestInfoByCondition(GuestInfo guestInfo);
    /** 根据条件查询结果条数（处理分页时需要使用的数据）*/
    Integer queryGuestInfoCountByCondition(GuestInfo guestInfo);
}
