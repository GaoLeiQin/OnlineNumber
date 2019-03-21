package com.ledo.beans;

/**
 * 网页内容bean
 * @author qgl
 * @date 2018/10/14
 */
public class UrlContent {
    private String channel;
    private int zoneId;
    private String serverName;
    private int onlineNum;
    private int maxOnlineNum;

    public UrlContent() {
    }

    public UrlContent(String channel, int zoneId, String serverName, int onlineNum, int maxOnlineNum) {
        this.channel = channel;
        this.zoneId = zoneId;
        this.serverName = serverName;
        this.onlineNum = onlineNum;
        this.maxOnlineNum = maxOnlineNum;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(int onlineNum) {
        this.onlineNum = onlineNum;
    }

    public int getMaxOnlineNum() {
        return maxOnlineNum;
    }

    public void setMaxOnlineNum(int maxOnlineNum) {
        this.maxOnlineNum = maxOnlineNum;
    }

    @Override
    public String toString() {
        return "UrlContent" +
                " & channel = " + channel +
                " & zoneId = " + zoneId +
                " & serverName = " + serverName +
                " & onlineNum = " + onlineNum +
                " & maxOnlineNum = " + maxOnlineNum +
                " ### ";
    }
}
