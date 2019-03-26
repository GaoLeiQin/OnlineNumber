package com.ledo.service;

/**
 * 管理员service 接口
 * @author qgl
 * @date 2018/10/24
 */
public interface IAdministratorService {
    /** 新增服务器在线信息 */
    void addServerInfo();
    /** 开启自动更新任务 */
    void openAutoUpdateTask();
}
