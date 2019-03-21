package com.ledo.beans;

/**
 * 全部服务器信息bean
 * @author qgl
 * @date 2018/9/24
 */
public class AllServerInfo {
    private String channel;
    private Integer zoneId;
    private String serverName;
    private Integer optOrId;
    private String ip;
    private String hostName;
    private Integer onlineNum;
    private String openTime;
    private Integer openDays;

    public AllServerInfo(){}

    public AllServerInfo(String channel, Integer zoneId, String serverName, Integer optOrId, String ip, String hostName, Integer onlineNum) {
        this.channel = channel;
        this.zoneId = zoneId;
        this.serverName = serverName;
        this.optOrId = optOrId;
        this.ip = ip;
        this.hostName = hostName;
        this.onlineNum = onlineNum;
    }

    public AllServerInfo(String channel, Integer zoneId, String serverName, Integer optOrId, String ip, String hostName, Integer onlineNum, String openTime, Integer openDays) {
        this.channel = channel;
        this.zoneId = zoneId;
        this.serverName = serverName;
        this.optOrId = optOrId;
        this.ip = ip;
        this.hostName = hostName;
        this.onlineNum = onlineNum;
        this.openTime = openTime;
        this.openDays = openDays;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Integer getOptOrId() {
        return optOrId;
    }

    public void setOptOrId(Integer optOrId) {
        this.optOrId = optOrId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(Integer onlineNum) {
        this.onlineNum = onlineNum;
    }


    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public Integer getOpenDays() {
        return openDays;
    }

    public void setOpenDays(Integer openDays) {
        this.openDays = openDays;
    }

    @Override
    public String toString() {
        return "AllServerInfo{" +
                "channel='" + channel + '\'' +
                ", zoneId=" + zoneId +
                ", serverName='" + serverName + '\'' +
                ", optOrId=" + optOrId +
                ", ip='" + ip + '\'' +
                ", hostName='" + hostName + '\'' +
                ", onlineNum=" + onlineNum +
                ", openTime='" + openTime + '\'' +
                ", openDays='" + openDays + '\'' +
                '}';
    }
}
