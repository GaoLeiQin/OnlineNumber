package com.ledo.manager;

import java.util.ArrayList;

/**
 * 用户 工具类
 * @author qgl
 * @date 2018/11/11
 */
public class UserManager {
    private static final String ADMIN_USER_1 = "qingaolei";
    private static final String ADMIN_USER_2 = "liuxudong";

    /**
     * 判断该用户是否是管理员
     * @param userName
     * @return
     */
    public static boolean isAdmin(String userName) {
        boolean isAdmin = false;
        if (getAdminUserList().contains(userName.trim())) {
            isAdmin = true;
        }
        return isAdmin;
    }

    /**
     * 获取管理员用户名列表
     * @return
     */
    private static ArrayList<String> getAdminUserList() {
        ArrayList<String> adminUserList = new ArrayList<>();
        adminUserList.add(ADMIN_USER_1);
        adminUserList.add(ADMIN_USER_2);
        return adminUserList;
    }
}
