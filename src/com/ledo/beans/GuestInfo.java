package com.ledo.beans;

/**
 * 访客信息bean
 * @author qgl
 * @date 2018/10/30
 */
public class GuestInfo {
    private String date;
    private String userName;
    private String type;
    private String ip;
    private String userAgent;
    private Page page;

    public GuestInfo() {
    }

    public GuestInfo(String date, String userName, String type, String ip, String userAgent, Page page) {
        this.date = date;
        this.userName = userName;
        this.type = type;
        this.ip = ip;
        this.userAgent = userAgent;
        this.page = page;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "GuestInfo{" +
                "date='" + date + '\'' +
                ", userName='" + userName + '\'' +
                ", type='" + type + '\'' +
                ", ip='" + ip + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", page=" + page +
                '}';
    }
}
