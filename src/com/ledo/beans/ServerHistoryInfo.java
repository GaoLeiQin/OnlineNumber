package com.ledo.beans;

/**
 * 服务器历史数据bean
 * @author qgl
 * @date 2018/9/24
 */
public class ServerHistoryInfo {
    private Integer id;
    private String date;
    private Integer officialNum;
    private Integer mixNum;
    private Integer gatNum;
    private Integer totalNum;
    private Page page;

    public ServerHistoryInfo() {
    }

    public ServerHistoryInfo(String date, Integer officialNum, Integer mixNum, Integer gatNum, Integer totalNum) {
        this.date = date;
        this.officialNum = officialNum;
        this.mixNum = mixNum;
        this.gatNum = gatNum;
        this.totalNum = totalNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getOfficialNum() {
        return officialNum;
    }

    public void setOfficialNum(Integer officialNum) {
        this.officialNum = officialNum;
    }

    public Integer getMixNum() {
        return mixNum;
    }

    public void setMixNum(Integer mixNum) {
        this.mixNum = mixNum;
    }

    public Integer getGatNum() {
        return gatNum;
    }

    public void setGatNum(Integer gatNum) {
        this.gatNum = gatNum;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "ServerHistoryInfo{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", officialNum=" + officialNum +
                ", mixNum=" + mixNum +
                ", gatNum=" + gatNum +
                ", totalNum=" + totalNum +
                ", page=" + page +
                '}';
    }
}
