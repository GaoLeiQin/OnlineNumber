package com.ledo.common;

/**
 * Http请求相关常量
 * @author qgl
 * @date 2018/11/16
 */
public class URLConstant {
    /** 网页的 IP 地址 */
    public static final String OFFICIAL = "大陆官服";
    public static final String OFFICIAL_IP = "http://124.243.220.123:29034/ServerStatus";
    public static final String MIX = "大陆硬核与混服";
    public static final String MIX_IP = "http://124.243.220.124:29034/ServerStatus";
    public static final String GAT = "港澳台";
    public static final String GAT_IP = "http://220.229.229.38:29034/ServerStatus";
    public static final String CONTENT_SPLIT_SIGNAL = "##";
    public static final String OFFICIAL_CHANNEL = "LEDO";
    public static final String MIX_HARDALLIANCE_CHANNEL = "硬核";
    public static final String MIX_ANDROID_CHANNEL = "混服";
    public static final String GAT_CHANNEL = "GAT_LEDO";

    // http请求参数
    /** Socket 超时时间 50 s，因为网页内容每分钟更新一次，每次更新需要3秒左右*/
    public static final int SOCKET_TIMEOUT = 50 * 1000;
    /** 在线服务器的最少数目 */
    public static final int ONLINE_SERVER_SUM = 50;
    /** 本机的IP */
    public static final String LOCAL_HOST_IP = "127.0.0.1";
    /** Http请求头中Referer参数表示上一次请求网页的地址 */
    public static final String HEADER_PARAM_REFERER = "Referer";
    /**数据库保存 UserAgent 字段的最大长度*/
    public static final int USER_AGENT_MAX_LENGTH = 100;

}
