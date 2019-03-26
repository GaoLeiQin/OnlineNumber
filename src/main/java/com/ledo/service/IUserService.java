package com.ledo.service;

import com.ledo.beans.User;

/**
 * 用户service 接口
 * @author qgl
 * @date 2018/11/16
 */
public interface IUserService {
    /** 查询用户名是否存在 */
    Integer referUserNameCount(User user);
    /** 根据用户名查询用户的密码 */
    Integer verifyUserInfo(User user);
}
