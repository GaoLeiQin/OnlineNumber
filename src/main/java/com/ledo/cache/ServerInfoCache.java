package com.ledo.cache;

import com.ledo.beans.ServerHistoryInfo;

/**
 * 缓存最新的服务器在线人数信息
 * @author qgl
 * @date 2018/12/17
 */
public class ServerInfoCache {
    private static ServerInfoCache instance = new ServerInfoCache();
    private ServerHistoryInfo serverHistoryInfo;

    public static ServerInfoCache getInstance() {
        return instance;
    }

    public ServerHistoryInfo getServerHistoryInfo() {
        return serverHistoryInfo;
    }

    public void setServerHistoryInfo(ServerHistoryInfo serverHistoryInfo) {
        this.serverHistoryInfo = serverHistoryInfo;
    }
}
