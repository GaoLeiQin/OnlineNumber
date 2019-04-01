package com.ledo.manager;

import com.ledo.beans.GuestInfo;
import com.ledo.beans.UrlContent;
import com.ledo.util.DateUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.ledo.common.URLConstant.*;

/** 网页相关方法
 * @author qgl
 * @date 2018/10/9
 */
public class URLManager extends BaseManager{
    private static URLManager instance = new URLManager();

    public static URLManager getInstance() {
        return instance;
    }

    /**
     * 保存URL信息
     * @return
     */
    public HashMap<String, URL> getUrl() {
        HashMap<String, URL> urlChannel = new HashMap<>(3);
        try {
            urlChannel.put(OFFICIAL, new URL(OFFICIAL_IP));
            urlChannel.put(MIX, new URL(MIX_IP));
            urlChannel.put(GAT, new URL(GAT_IP));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return urlChannel;
    }

    /**
     * 获取网页内容
     * @param url 网址
     * @return
     */
    public ArrayList<UrlContent> getUrlContents(URL url) {
        BufferedReader br = null;
        InputStreamReader isr = null;
        ArrayList<UrlContent> contents = new ArrayList<>();

        try {
            URLConnection urlConnection = url.openConnection();
            // 设置超时时间
            urlConnection.setConnectTimeout(SOCKET_TIMEOUT);
            isr = new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8);
            br = new BufferedReader(isr);
            String data = br.readLine();

            while (data != null) {
                String[] info = data.split(CONTENT_SPLIT_SIGNAL);
                if (info.length > 4) {
                    String channelName = info[0];
                    int id = Integer.valueOf(info[1].trim());
                    String serverName = info[2];
                    int onlineNum = Integer.valueOf(info[3].trim());
                    int maxOnlineNum = Integer.valueOf(info[4].trim().substring(0, 4));
                    contents.add(new UrlContent(channelName, id, serverName, onlineNum, maxOnlineNum));
                }

                data = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("访问超时！！！ IP：" + url.getHost() + "  " + e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return contents;
    }

    /**
     * 获取ip地址
     * @param request
     * @return
     */
    private String getIpAddress(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String strIp : ips) {
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }

        //若是本机，则根据网卡取本机配置的IP
        if(ip.equals(LOCAL_HOST_IP)){
            InetAddress inet=null;
            try {
                inet = InetAddress.getLocalHost();
            } catch (Exception e) {
                logger.error("不能读取本机IP " + e);
            }
            if (inet != null) {
                ip= inet.getHostAddress();
            }
        }

        return ip;
    }

    /**
     * 根据IP封装访客信息
     * @param request
     * @return
     */
    public GuestInfo getIncreaseGuestInfo(HttpServletRequest request, String userName) {
        String name = userName == null ? "刷新" : userName;
        GuestInfo guestInfo = new GuestInfo();
        guestInfo.setDate(DateUtil.getNowFormatDate());
        guestInfo.setUserName(name);
        guestInfo.setType(request.getRequestURI().substring(16).substring(0,5));
        guestInfo.setIp(this.getIpAddress(request));
        String userAgent = request.getHeader("User-Agent").substring(13);
        if (userAgent.length() > USER_AGENT_MAX_LENGTH) {
            userAgent = userAgent.substring(0, USER_AGENT_MAX_LENGTH);
        }
        guestInfo.setUserAgent(userAgent);
        return guestInfo;
    }

    /**
     * 根据在线人数对网页内容进行排序
     * @param allUrlContents
     */
    public void sortAllUrlContensByOnlineNum(ArrayList<UrlContent> allUrlContents) {
        if (allUrlContents == null || allUrlContents.size() == 0) {
            return;
        }

        Collections.sort(allUrlContents, new Comparator<UrlContent>() {
            @Override
            public int compare(UrlContent o1, UrlContent o2) {
                if (o1.getOnlineNum() < o2.getOnlineNum()) {
                    return 1;
                }else if (o1.getOnlineNum() == o2.getOnlineNum()) {
                    if (o1.getZoneId() > o2.getZoneId()) {
                        return 1;
                    }
                }else {
                    return -1;
                }
                return 0;
            }
        });
    }

    /**
     * 判断是否通过跳转访问，而非直接访问该项目地址
     * @param request 访问的地址
     * @return 若通过跳转则可以访问
     */
    public boolean canVisit(HttpServletRequest request) {
        return request.getHeader(HEADER_PARAM_REFERER) != null;
    }

    /**
     * 根据不同渠道分别保存网页内容
     * @param urlContentsMapByCondition
     * @param officialContents
     * @param androidContents
     * @param hardAllianceContents
     * @param gatContents
     */
    public void setContentsByCondition(HashMap<String, ArrayList<UrlContent>> urlContentsMapByCondition, ArrayList<UrlContent> officialContents,
                                       ArrayList<UrlContent> androidContents, ArrayList<UrlContent> hardAllianceContents, ArrayList<UrlContent> gatContents) {
        for (Map.Entry<String, ArrayList<UrlContent>> urlContentMapEntry: urlContentsMapByCondition.entrySet()) {
            String channel = urlContentMapEntry.getKey();
            ArrayList<UrlContent> urlContents = urlContentMapEntry.getValue();
            if (channel.equals(MIX_ANDROID_CHANNEL)) {
                androidContents.addAll(urlContents);
            }else if (channel.equals(MIX_HARDALLIANCE_CHANNEL)) {
                hardAllianceContents.addAll(urlContents);
            }else if (channel.equals(GAT_CHANNEL)) {
                gatContents.addAll(urlContents);
            }else if (channel.equals(OFFICIAL_CHANNEL)){
                officialContents.addAll(urlContents);
            }else {
                logger.error(" ****** 未知渠道 ******");
            }
        }
    }

}
