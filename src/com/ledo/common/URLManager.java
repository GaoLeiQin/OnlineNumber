package com.ledo.common;

import com.ledo.beans.GuestInfo;
import com.ledo.beans.Page;
import com.ledo.beans.UrlContent;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static com.ledo.common.ServerConstant.*;

/** 网页相关方法
 * @author qgl
 * @date 2018/10/9
 */
public class URLManager {
    /** 在线服务器的最少数目 */
    public static final int ONLINE_SERVER_SUM = 50;
    /** 本机的IP */
    public static final String LOCAL_HOST_IP = "127.0.0.1";
    /** Http请求头中Referer参数表示上一次请求网页的地址 */
    public static final String HEADER_PARAM_REFERER = "Referer";
    /**数据库保存 UserAgent 字段的最大长度*/
    public static final int USERAGENT_MAX_LENGTH = 100;
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
            //通过URL的openStrean方法获取URL对象所表示的字节输入流
            isr = new InputStreamReader(url.openStream(), "utf-8");

            //为字符输入流添加缓冲
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
     * 根据不同渠道分别保存网页内容
     * @param urlContentsMapByCondition
     * @param officialContents
     * @param androidContents
     * @param hardAllianceContents
     * @param gatContents
     */
    public static void setContentsByCondition(HashMap<String, ArrayList<UrlContent>> urlContentsMapByCondition, ArrayList<UrlContent> officialContents,
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
                System.err.println(" ****** 未知渠道 ******");
            }
        }
    }

    /**
     * 处理页面
     * @param p
     * @param size
     */
    public static Page setPageInfo(Page p, int size) {
        Page page = p;
        //设置每页大小
        int pageSize = 25;
        page.setPageSize(pageSize);
        Integer currentPage = page.getCurrentPage();

        if (currentPage == null || currentPage <= 0) {
            currentPage=1;
            page.setCurrentPage(currentPage);
        }
        int startRow = currentPage == 1 ? 0 : (currentPage-1)*pageSize;
        page.setStartRow(startRow);
        int totalCounts = size;
        int totalPages = (totalCounts % pageSize == 0) ? (totalCounts/pageSize):(totalCounts/pageSize+1);//总页数=总条数/页大小+1
        page.setTotalPage(totalPages);//总页数
        page.setTotalRows(totalCounts);//总行数
        return page;
    }

    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
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
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
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
                e.printStackTrace();
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
    public static GuestInfo increaseGuestInfo(HttpServletRequest request, String userName) {
        String name = userName == null ? "" : userName;
        GuestInfo guestInfo = new GuestInfo();
        guestInfo.setDate(FileManager.getNowFormatDate());
        guestInfo.setUserName(name);
        guestInfo.setType(request.getRequestURI().substring(14).substring(0,5));
        guestInfo.setIp(URLManager.getIpAddress(request));
        String userAgent = request.getHeader("User-Agent").substring(13);
        if (userAgent.length() > USERAGENT_MAX_LENGTH) {
            userAgent = userAgent.substring(0, USERAGENT_MAX_LENGTH);
        }
        guestInfo.setUserAgent(userAgent);
        return guestInfo;
    }

    /**
     * 判断网页是否是自动刷新，通过http请求头中的referer参数获取上一次请求网页的地址
     * @param request
     * @return
     */
    public static boolean isRefresh(HttpServletRequest request) {
        if (request.getHeader(HEADER_PARAM_REFERER) == null) {
            return true;
        }
        return request.getHeader(HEADER_PARAM_REFERER).equals(String.valueOf(request.getRequestURL()));
    }

    /**
     * 根据在线人数对网页内容进行排序
     * @param allUrlContents
     */
    public static void sortAllUrlContensByOnlineNum(ArrayList<UrlContent> allUrlContents) {
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
                }
                return -1;
            }
        });
    }

    /******************************* 废弃 根据网页内容计算最多和最少在线人数的方法，
     * 将网页内容保存到数据库中，然后进行条件查询，省去计算的步骤*****************************************/
    @Deprecated
    private int allSum;
    private HashMap<String, Integer> serverInfo;
    private HashMap<String, HashMap<UrlContent, UrlContent>> maxAndMin;

    public void init() {
        allSum = 0;
        serverInfo = new HashMap<>(3);
        maxAndMin = new HashMap<>(3);
        getServerInfoMap();
    }

    /**
     * 保存相关数据
     */
    private void getServerInfoMap() {
        HashMap<String, URL> urls = URLManager.getInstance().getUrl();
        for (Map.Entry<String, URL> info : urls.entrySet()) {
            String serverName = info.getKey();
            URL url = info.getValue();
            ArrayList<UrlContent> contents = URLManager.getInstance().getUrlContents(url);
            sortByOnlineNum(contents);
            int num = computeSum(contents);
            serverInfo.put(serverName, num);
            maxAndMin.put(serverName, getMaxAndMinMap(contents));
            allSum += num;
        }
    }

    /**
     * 保存服务器最多和最少在线人数信息
     * @param sortedContents 排序后的服务器列表
     * @return
     */
    private HashMap<UrlContent, UrlContent> getMaxAndMinMap(ArrayList<UrlContent> sortedContents) {
        HashMap<UrlContent, UrlContent> maxOrMin = new HashMap<>(1);
        if (sortedContents.size() < 1) {
            return null;
        }
        UrlContent maxInfo = sortedContents.get(0);
        UrlContent minInfo = sortedContents.get(sortedContents.size() - 1);
        maxOrMin.putIfAbsent(maxInfo, minInfo);
        return maxOrMin;
    }

    /**
     * 根据服务器在线人数排序
     *
     * @param contents
     */
    private void sortByOnlineNum(ArrayList<UrlContent> contents) {
        if (contents == null) {
            return;
        }

        Collections.sort(contents, new Comparator<UrlContent>() {
            @Override
            public int compare(UrlContent o1, UrlContent o2) {
                if (o1.getOnlineNum() < o2.getOnlineNum()) {
                    return 1;
                } else if (o1.getOnlineNum() == o2.getOnlineNum()) {
                    if (o1.getZoneId() > o2.getZoneId()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                return -1;
            }
        });
    }

    /**
     * +
     * 计算在线人数总数
     *
     * @param contents
     * @return
     */
    private int computeSum(ArrayList<UrlContent> contents) {
        int sum = 0;
        if (contents == null) {
            return sum;
        }

        for (UrlContent content : contents) {
            sum += content.getOnlineNum();
        }

        return sum;
    }
}
