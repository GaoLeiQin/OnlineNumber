package com.ledo.service;

import com.ledo.beans.Page;
import com.ledo.beans.RechargeInfo;

import java.util.ArrayList;

public interface IRechargeInfoService {
    ArrayList<RechargeInfo> referRechargeInfo();
    ArrayList<RechargeInfo> referRechargeInfoByPage(Page page);
}