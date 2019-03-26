package com.ledo.dao;

import com.ledo.beans.RechargeInfo;

import java.util.ArrayList;

public interface IRecharge {
    /** 删除充值数据 */
    void deleteRechargeInfo();
    /** 插入充值数据 */
    void insertRechargeInfo(RechargeInfo rechargeInfo);
    /** 查询所有充值信息 */
    ArrayList<RechargeInfo> queryRechargeInfo();
    /** 对查询的充值信息进行分页 */
    ArrayList<RechargeInfo> queryRechargeInfoByPage(RechargeInfo rechargeInfo);
    /** 统计查询结果的条数 */
    Integer queryRechargeInfoCountByCondition(RechargeInfo historyByCondition);
}
