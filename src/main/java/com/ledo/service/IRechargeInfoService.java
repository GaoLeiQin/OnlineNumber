package com.ledo.service;

import com.ledo.beans.RechargeInfo;

import java.util.ArrayList;

public interface IRechargeInfoService {
    /** 删除充值数据 */
    void deleteRechargeInfo();
    /** 更新充值数据 */
    void updateRechargeInfo();
    /** 查询充值数据 */
    ArrayList<RechargeInfo> referRechargeInfo();
    /** 对查询结果进行分页处理 */
    ArrayList<RechargeInfo> referRechargeInfoByPage(RechargeInfo rechargeInfo);
    /** 统计查询结果的条数 */
    Integer referRechargeInfoCountByCondition(RechargeInfo historyByCondition);

}