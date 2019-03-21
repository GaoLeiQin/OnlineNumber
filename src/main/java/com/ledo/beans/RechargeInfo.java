package com.ledo.beans;

/**
 * 充值信息bean
 * @author qgl
 * @date 2018/10/9
 */
public class RechargeInfo {
    private int zoneId;
    private String date;
    private int userId;
    private int roleId;
    private int rmbNum;
    private String platId;
    private String platName;
    private String chargePlatSn;
    private int chargeGameSn;

    public RechargeInfo(){}

    public RechargeInfo(int zoneId, String date, int userId, int roleId, int rmbNum, String platId, String platName, String chargePlatSn, int chargeGameSn) {
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

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getRmbNum() {
        return rmbNum;
    }

    public void setRmbNum(int rmbNum) {
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

    public int getChargeGameSn() {
        return chargeGameSn;
    }

    public void setChargeGameSn(int chargeGameSn) {
        this.chargeGameSn = chargeGameSn;
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
                '}';
    }
}
