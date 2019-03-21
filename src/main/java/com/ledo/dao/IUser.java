package com.ledo.dao;

import com.ledo.beans.User;

/**
 * 用户信息 接口
 * @author qgl
 * @date 2018/11/10
 */
public interface IUser {
    /** 查询用户名是否存在 */
    Integer queryUserNameCount(User user);

    /** 根据用户名查询用户的密码 */
    Integer verifyUser(User user);
}
