package com.ledo.service;

import com.ledo.beans.User;
import com.ledo.dao.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/** 用户信息 service
 * @author qgl
 * @date 2018/9/24
 */
@Service("userService")
public class UserServiceImpl extends BaseService implements IUserService {
    @Autowired
    @Qualifier("IUser")
    private IUser userDao;

    @Override
    public Integer referUserNameCount(User user) {
        return userDao.queryUserNameCount(user);
    }

    @Override
    public Integer verifyUserInfo(User user) {
        return userDao.verifyUser(user);
    }

}
