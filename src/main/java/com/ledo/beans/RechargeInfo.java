package com.ledo.beans;

/**
 * 充值信息bean
 * @author qgl
 * @date 2018/10/9
 */
public class RechargeInfo {
    private Integer zoneId;
    private String date;
    private Integer userId;
    private Integer roleId;
    private Integer rmbNum;
    private String platId;
    private String platName;
    private String chargePlatSn;
    private Integer chargeGameSn;
    private Page page;

    public RechargeInfo(){}

    public RechargeInfo(Integer zoneId, String date, Integer userId, Integer roleId, Integer rmbNum, String platId, String platName, String chargePlatSn, Integer chargeGameSn) {
        this.zoneId = zoneId;
        this.date = date;
        this.userId = userId;
        this.roleId = roleId;
        this.rmbNum = rmbNum;
        this.platId = platId;
        this.platName = platName;
        this.chargePlatSn = chargePlatSn;
        this.chargeGameSn = chargeGameSn;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRmbNum() {
        return rmbNum;
    }

    public void setRmbNum(Integer rmbNum) {
        this.rmbNum = rmbNum;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getChargePlatSn() {
        return chargePlatSn;
    }

    public void setChargePlatSn(String chargePlatSn) {
        this.chargePlatSn = chargePlatSn;
    }

    public Integer getChargeGameSn() {
        return chargeGameSn;
    }

    public void setChargeGameSn(Integer chargeGameSn) {
        this.chargeGameSn = chargeGameSn;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "RechargeInfo{" +
                "zoneId=" + zoneId +
                ", date='" + date + '\'' +
                ", userId=" + userId +
                ", roleId=" + roleId +
                ", rmbNum=" + rmbNum +
                ", platId='" + platId + '\'' +
                ", platName='" + platName + '\'' +
                ", chargePlatSn='" + chargePlatSn + '\'' +
                ", chargeGameSn=" + chargeGameSn +
                ", page=" + page +
                '}';
    }
}
