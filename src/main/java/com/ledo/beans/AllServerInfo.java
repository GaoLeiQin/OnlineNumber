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
    private String innerIp;
    private String outerIp;
    private String hostName;
    private String openTime;
    private Integer openDays;

    public AllServerInfo(){}

    public AllServerInfo(String channel, Integer zoneId, String serverName, Integer optOrId, String innerIp, String outerIp, String hostName, String openTime, Integer openDays) {
        this.channel = channel;
        this.zoneId = zoneId;
        this.serverName = serverName;
        this.optOrId = optOrId;
        this.innerIp = innerIp;
        this.outerIp = outerIp;
        this.hostName = hostName;
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

    public String getInnerIp() {
        return innerIp;
    }

    public void setInnerIp(String innerIp) {
        this.innerIp = innerIp;
    }

    public String getOuterIp() {
        return outerIp;
    }

    public void setOuterIp(String outerIp) {
        this.outerIp = outerIp;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
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
                ", innerIp='" + innerIp + '\'' +
                ", outerIp='" + outerIp + '\'' +
                ", hostName='" + hostName + '\'' +
                ", openTime='" + openTime + '\'' +
                ", openDays=" + openDays +
                '}';
    }
}
