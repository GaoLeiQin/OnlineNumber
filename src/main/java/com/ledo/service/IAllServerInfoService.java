package com.ledo.service;

import com.ledo.beans.AllServerInfo;
import com.ledo.beans.Page;

import java.util.ArrayList;

public interface IAllServerInfoService {
    void onlyUpdateOnlineNumbersInfo();
    ArrayList<AllServerInfo> referAllServerInfo();
    ArrayList<AllServerInfo> referServerInfoByCondition(AllServerInfo serverInfo);

}