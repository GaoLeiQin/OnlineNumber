package com.ledo.service;

import com.ledo.beans.Page;
import com.ledo.beans.RechargeInfo;
import com.ledo.dao.IRecharge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import static com.ledo.common.FileConstant.RECHARGE_LOG_PATH;

/**
 * 查询充值数据service
 * @author qgl
 * @date 2018/10/9
 */
@Service("rechargeInfoService")
public class RechargeInfoServiceImpl implements IRechargeInfoService{
    @Autowired
    @Qualifier("IRecharge")
    private IRecharge rechargeDao;

    @Override
    public ArrayList<RechargeInfo> referRechargeInfo() {
        return rechargeDao.queryRechargeInfo();
    }

    @Override
    public ArrayList<RechargeInfo> referRechargeInfoByPage(Page page) {
        return rechargeDao.queryRechargeInfoByPage(page);
    }

}
