package com.ledo.service;

import com.ledo.beans.RechargeInfo;
import com.ledo.dao.IRecharge;
import com.ledo.manager.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 查询充值数据service
 * @author qgl
 * @date 2018/10/9
 */
@Service("rechargeInfoService")
public class RechargeInfoServiceImpl extends BaseService implements IRechargeInfoService{
    @Autowired
    @Qualifier("IRecharge")
    private IRecharge rechargeDao;

    @Override
    public ArrayList<RechargeInfo> referRechargeInfo() {
        return rechargeDao.queryRechargeInfo();
    }

    @Override
    public ArrayList<RechargeInfo> referRechargeInfoByPage(RechargeInfo rechargeInfo) {
        return rechargeDao.queryRechargeInfoByPage(rechargeInfo);
    }

    @Override
    public Integer referRechargeInfoCountByCondition(RechargeInfo rechargeInfo) {
        return rechargeDao.queryRechargeInfoCountByCondition(rechargeInfo);
    }

    @Override
    public void deleteRechargeInfo() {
        rechargeDao.deleteRechargeInfo();
    }

    @Override
    public void updateRechargeInfo() {
        ArrayList<RechargeInfo> allFilesRechargeInfoList = FileManager.getInstance().getAllFilesRechargeInfo();
        for (RechargeInfo rechargeInfo : allFilesRechargeInfoList) {
            rechargeDao.insertRechargeInfo(rechargeInfo);
        }
    }

}
