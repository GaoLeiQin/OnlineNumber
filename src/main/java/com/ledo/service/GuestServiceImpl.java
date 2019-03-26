package com.ledo.service;

import com.ledo.beans.GuestInfo;
import com.ledo.dao.IGuest;
import com.ledo.manager.URLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * 访客service 具体实现
 * @author qgl
 * @date 2018/11/16
 */
@Service("guestService")
public class GuestServiceImpl extends BaseService implements IGuestService{
    @Autowired
    @Qualifier("IGuest")
    private IGuest guestDao;

    @Override
    public void addGuestInfo(HttpServletRequest request, String userName) {
        guestDao.insertGuestInfo(URLManager.getInstance().getIncreaseGuestInfo(request, userName));
    }

    @Override
    public ArrayList<GuestInfo> queryGuestInfo() {
        return guestDao.queryGuestInfo();
    }

    @Override
    public ArrayList<GuestInfo> queryGuestInfoByCondition(GuestInfo guestInfo) {
        return guestDao.queryGuestInfoByCondition(guestInfo);
    }

    @Override
    public Integer queryGuestInfoCountByCondition(GuestInfo guestInfo) {
        return guestDao.queryGuestInfoCountByCondition(guestInfo);
    }
}
