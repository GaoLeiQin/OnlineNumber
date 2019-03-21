package com.ledo.dao;

import com.ledo.beans.Page;
import com.ledo.beans.RechargeInfo;

import java.util.ArrayList;

public interface IRecharge {
    ArrayList<RechargeInfo> queryRechargeInfo();
    ArrayList<RechargeInfo> queryRechargeInfoByPage(Page page);
}
