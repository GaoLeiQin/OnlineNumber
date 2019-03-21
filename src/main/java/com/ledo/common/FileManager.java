package com.ledo.common;

import com.ledo.beans.ServerHistoryInfo;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件相关操作
 * @author qgl
 * @date 2018/9/24
 */
public class FileManager {
    static Logger logger = Logger.getLogger(FileManager.class);

    public static final String RECHARGE_LOG_PATH = "E:\\papers\\companyfile\\log\\Recharge_log";
    public static final String ZONE_OPT_PATH = "E:\\papers\\companyfile\\log\\zoneOpt.txt";
    public static final String SERVER_OPENDAYS_PATH = "E:\\papers\\companyfile\\log\\serverOpenDays.txt";

    /**
     * 获取开服天数
     * @param serverOpenTime
     * @return
     */
    public static int getServerOpenDays(String serverOpenTime) {
        int openDays = 0;
        long now = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long openTime = 0;
        try {
            openTime = simpleDateFormat.parse(serverOpenTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        openDays = getFormatTime(now - openTime);
        return openDays;
    }

    /**
     * 将毫秒数转换为 x天x小时x分钟x秒
     * @param time
     * @return
     */
    private static int getFormatTime(long time) {
        long second = time / 1000;
        long minute = 0;
        long hour = 0;
        long day = 0;

        if (second > 60) {
            minute = second / 60;
            second = second % 60;
            if (minute > 60) {
                hour = minute / 60;
                minute = minute % 60;
                if (hour > 24) {
                    day = hour / 24;
                    hour = hour % 24;
                }
            }
        }

        return (int) day;
    }

    /**
     * 获取当前时间的日期串
     * @return 日期的类型：yyyy-MM-dd HH:mm:ss
     */
    public static String getNowFormatDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取字符串中的所有数字
     * @param category
     * @return int
     */
    public static int getNumber(String category) {
        String numberRegex = "[^0-9]";
        Pattern p = Pattern.compile(numberRegex);
        Matcher m = p.matcher(category);
        String target = m.replaceAll("");
        return Integer.valueOf(target);
    }

    /**
     * 获取历史数据中的日期
     * @param historyInfos
     * @return
     */
    public ArrayList<String> getStrTime(ArrayList<ServerHistoryInfo> historyInfos) {
        ArrayList<String> strTime = new ArrayList<>(25);
        for (ServerHistoryInfo history : historyInfos) {
            strTime.add(history.getDate());
        }
        return strTime;
    }

    /**
     * 将日期串转换毫秒数
     * @param strTime 字符串
     * @return 毫秒
     */
    public static ArrayList<Long> getMilliSecondByFormatDate(ArrayList<String> strTime) {
        ArrayList<Long> milliSecondList = new ArrayList<>(25);
        for (String time : strTime) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                long date = simpleDateFormat.parse(time).getTime();
                milliSecondList.add(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return milliSecondList;
    }

    /**
     * 根据毫秒数返回标准的日期串
     * @param timeString 毫秒
     * @return 标准日期
     */
    public static String getFormatDateByMillSecond(String timeString) {
        Date date = new Date(Long.valueOf(timeString));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 通过文件名截取到 zoneId
     * @param name 文件名
     * @return zoneId
     */
    public static int getZoneIdByFilename(String name) {
        int zoneId = 0;
        String fileName = name.substring(39).split("\\.")[0];
        if (fileName.contains("gat")) {
            String[] gatNames = fileName.split("_");
            zoneId =  Integer.valueOf(gatNames[gatNames.length - 1]);
        }else {
            String[] names = fileName.split("=");
            zoneId = Integer.valueOf(names[0]);
        }

        return zoneId;
    }

    /**
     * 用来区分大陆和港澳台，（有部分ID相同）
     * @param zoneNameAndId
     * @param zoneOpt
     * @param content
     * @param isGat
     *//*
    public static void adddata(HashMap<Integer, String> zoneNameAndId, HashMap<String, String> zoneOpt, UrlContent content, boolean isGat) {
        String channel = content.getChannelName();
        if (isGat) {
            channel = "GAT" + content.getChannelName();
        }
        int zoneId = content.getId();
        String serverName = content.getServerName();

        int optOrId = 0;
        String ip = null;
        String hostName = null;

        if (zoneNameAndId.containsKey(zoneId)){
            hostName = zoneNameAndId.get(zoneId);
            if (zoneOpt.containsKey(hostName)) {
                String[] idAndIp = zoneOpt.get(hostName).split("=");
                if (idAndIp.length > 1) {
                    optOrId = Integer.valueOf(idAndIp[0]);
                    ip = idAndIp[1];
                }
                // 保存服务器相关数据
                System.out.println(channel + " " + zoneId + " " + serverName + " | " +
                        optOrId + " " + ip + " " + hostName);
            }

        }
    }

    @Test
    public void print() {
//        HashMap<String, Integer> zoneNameAndId = getZoneIdAndName(DIRECTORY_NAME);
        HashMap<String, String> zoneOpt = getZoneOpt(ZONE_OPT_PATH);
        int count = 0;
        for (Map.Entry<String, String> zone : zoneOpt.entrySet()) {
            String[] idAndIp = zone.getValue().split("=");
            if (idAndIp.length > 1) {
                System.out.println(idAndIp[0] + "  " + idAndIp[1] + "  " + zone.getKey());
            }
            count++;
        }
        System.err.println(count);

    }

    *//**
     * 获取Linux服务器ID与对应的name
     * @param directoryName
     *//*
    public static HashMap<Integer, String> getZoneIdAndName(String directoryName) {
        File[] files = new File(directoryName).listFiles();
        if (files == null) {
            return null;
        }

        HashMap<Integer, String> zoneNameAndId = new HashMap<>(128);
        for (File file : files) {
            String fileName = file.getName().split("\\.")[0];
//            boolean isBeta = fileName.contains("test") || fileName.contains("tishen") || fileName.contains("null");

            if (!fileName.contains("gat")) {
                String[] names = fileName.split("=");
                int serverId = Integer.valueOf(names[0]);
                String serverName = names[names.length - 1];
                zoneNameAndId.putIfAbsent(serverId, serverName);
            }
        }
        return zoneNameAndId;
    }

    *//**
     * 获取 港澳台 Linux服务器ID与对应的name
     * @param directoryName
     *//*
    public static HashMap<Integer, String> getGatZoneIdAndName(String directoryName) {
        File[] files = new File(directoryName).listFiles();
        if (files == null) {
            return null;
        }
        HashMap<Integer, String> zoneNameAndId = new HashMap<>(128);
        for (File file : files) {
            String fileName = file.getName().split("\\.")[0];
            if (fileName.contains("gat")) {
                String[] gatNames = fileName.split("_");
                int gatId = Integer.valueOf(gatNames[gatNames.length - 1]);
                String gatName = gatNames[gatNames.length - 2];
                zoneNameAndId.putIfAbsent(gatId, gatName);
            }
        }
        return zoneNameAndId;
    }

    *//**
     * 读取文件中每一行的内容
     * @param fileName
     *//*
    public static HashMap<String, String> getZoneOpt(String fileName) {
        HashMap<String, String> zoneOpt = new HashMap<>(128);
        BufferedReader br = null;
        try {
            // 读取充值数据
            br = new BufferedReader(new FileReader(fileName));
            String str = null;

            while ((str = br.readLine()) != null) {
                String content = str;
                if (content.contains("[jp_admin]")) {
                    int optOrId = getNumber(content.substring(0,5));
                    String ip = content.substring(5,20);
                    int index = content.indexOf("[jp_admin]");
                    String serverName = content.substring(index - 32, index);
                    zoneOpt.putIfAbsent(serverName, optOrId + "=" + ip);
                }
            }

        } catch (IOException e) {
            logger.error(e);
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                logger.error(e);
            }
        }

        return zoneOpt;
    }*/

}
