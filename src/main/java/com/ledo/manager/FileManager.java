package com.ledo.manager;

import com.ledo.beans.RechargeInfo;
import com.ledo.util.DateUtil;
import com.ledo.util.RegularUtil;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.ledo.common.FileConstant.RECHARGE_LOG_DIRECTORY;

/**
 * 解析日志文件和Linux服务器信息文件操作
 * @author qgl
 * @date 2018/9/24
 */
public class FileManager extends BaseManager{
    public static FileManager instance = new FileManager();

    public static FileManager getInstance() {
        return instance;
    }

    /********************************** Linux服务器信息相关文件内容处理方法 *****************************************/

    /**
     * 获取大陆Linux服务器ID与对应的name
     * @param directoryName
     */
    public HashMap<Integer, String> getInlandZoneIdAndName(String directoryName) {
        File[] files = new File(directoryName).listFiles();
        if (files == null) {
            return null;
        }

        HashMap<Integer, String> zoneNameAndId = new HashMap<>(128);
        for (File file : files) {
            String fileName = file.getName().split("\\.")[0];
            // 区分是否是测试或提审的数据
            boolean isBeta = fileName.contains("test") || fileName.contains("tishen") || fileName.contains("null");

            if (!fileName.contains("gat") && !isBeta) {
                String[] names = fileName.split("=");
                int serverId = Integer.valueOf(names[0]);
                String serverName = names[names.length - 1];
                zoneNameAndId.putIfAbsent(serverId, serverName);
            }
        }
        return zoneNameAndId;
    }

    /**
     * 获取 港澳台 Linux服务器ID与对应的name
     * @param directoryName
     */
    public HashMap<Integer, String> getGatZoneIdAndName(String directoryName) {
        File[] files = new File(directoryName).listFiles();
        if (files == null) {
            return null;
        }
        HashMap<Integer, String> zoneNameAndId = new HashMap<>(64);
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

    /**
     * 从opt文件中获取Linux服务器主机名、IP等信息
     * @param fileName
     */
    public HashMap<String, String> getZoneOpt(String fileName) {
        HashMap<String, String> zoneOpt = new HashMap<>(128);
        BufferedReader br = null;
        try {
            // 读取Linux服务器信息
            br = new BufferedReader(new FileReader(fileName));
            String str = null;

            while ((str = br.readLine()) != null) {
                String content = str;
                if (content.contains("[jp_admin]")) {
                    int optOrId = RegularUtil.getNumber(content.substring(0,5));
                    String ip = content.substring(5,20).trim();
                    int index = content.indexOf("[jp_admin]");
                    String serverName = content.substring(index - 32, index).trim();
                    zoneOpt.putIfAbsent(serverName, optOrId + "=" + ip);
                }
            }

        } catch (IOException e) {
            logger.error("解析文件：" + fileName + " 异常，" + e);
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
    }

    /**
     * 获取大陆服务器的开服时间
     * @param fileName
     */
    public HashMap<Integer, String> getInlandZoneIdAndOpenTime(String fileName) {
        HashMap<Integer, String> serverOpenDays = new HashMap<>(64);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String str = null;

            while ((str = br.readLine()) != null) {
                if (str.length() < 25) {
                    continue;
                }
                int zoneId = RegularUtil.getNumber(str.substring(0, 5));
                String openDays = str.substring(5, 25).trim();
                serverOpenDays.putIfAbsent(zoneId, openDays);
            }

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("不能获取大陆服务器开服时间，原因：" + e);
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return serverOpenDays;
    }

    /**
     * 获取港澳台服务器的开服时间（zoneId与大陆重复）
     * @param fileName
     */
    public HashMap<Integer, String> getGatZoneIdAndOpenTime(String fileName) {
        HashMap<Integer, String> gatOpenDays = new HashMap<>(64);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String str = null;

            while ((str = br.readLine()) != null) {
                if (str.length() < 25) {
                    continue;
                }
                if (str.contains("GAT")) {
                    int zoneId = RegularUtil.getNumber(str.substring(0, 5));
                    String openDays = str.substring(5, 25).trim();
                    gatOpenDays.putIfAbsent(zoneId, openDays);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("不能获取港澳台服务器开服时间，原因：" + e);
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return gatOpenDays;
    }

    /**
     * 获取开服天数
     * @param serverOpenTime
     * @return
     */
    public static int getServerOpenDays(String serverOpenTime) {
        int openDays = 0;
        try {
            openDays = DateUtil.getIntervalDays(serverOpenTime);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("获取开服天数失败 " + e);
        }
        return openDays;
    }

    /********************************** 充值信息相关文件内容处理方法 *****************************************/

    /**
     * 读取所有充值文件的充值信息内容
     * @return
     */
    public ArrayList<RechargeInfo> getAllFilesRechargeInfo() {
        ArrayList<RechargeInfo> allFilesRechargeInfoList = new ArrayList<>();
        File[] files = new File(RECHARGE_LOG_DIRECTORY).listFiles();
        if (files == null) {
            return allFilesRechargeInfoList;
        }

        for (File file : files) {
            String fileName = file.getAbsolutePath();
            boolean isBeta = fileName.contains("test") || fileName.contains("tishen") || fileName.contains("null");
            if (!isBeta) {
                ArrayList<RechargeInfo> rechargeInfoFileList = getFiles(fileName);
                allFilesRechargeInfoList.addAll(rechargeInfoFileList);
                // 测试：只读取一个文件
                break;
            }
        }
        return allFilesRechargeInfoList;
    }

    /**
     * 读取文件中每一行的内容
     * @param fileName
     */
    private ArrayList<RechargeInfo> getFiles(String fileName) {
        ArrayList<RechargeInfo> rechargeInfoFileList = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String str = null;

            while ((str = br.readLine()) != null) {
                rechargeInfoFileList.addAll(getRachargeInfo(str, getZoneIdByFilename(fileName)));
            }

        } catch (IOException e) {
            logger.error("解析文件：" + fileName + " 异常，" + e);
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                logger.error(e);
            }
        }
        return rechargeInfoFileList;
    }

    /**
     * 分类提取充值相关信息
     * @param content 文件的每一行内容
     * @param zoneId
     */
    private ArrayList<RechargeInfo> getRachargeInfo(String content, int zoneId) {
        ArrayList<RechargeInfo> rechargeInfoList = new ArrayList<>();
        String date = null;
        int userId = 0;
        int roleId = 0;
        int rmbNum = 0;
        String platId = null;
        String platName = null;
        String chargePlatSn = "-";
        int chargeGameSn = 0;

        // 港澳台充值信息(export/logs/charge.log日志文件)
        if (content.contains("ordersuccess")) {
            String[] strings = content.split(",");
            for (String category : strings) {
                if (category.contains("roleid")) {
                    date = category.substring(0, category.indexOf("INFO") - 1);
                    roleId = Integer.valueOf(category.substring(category.indexOf("roleid") + 7));

                } else if (category.contains("platUid")) {
                    if (category.split("\\$").length > 1) {
                        platId = String.valueOf(RegularUtil.getNumber(category.split("\\$")[0]));
                        platName = category.split("\\$")[1];
                    }

                } else if (category.contains("userid")) {
                    userId = RegularUtil.getNumber(category);

                } else if (category.contains("chargePlatSn")) {
                    if (category.split(":").length > 1) {
                        try {
                            chargePlatSn = new String(category.split(":")[1].getBytes("gbk"),"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (category.contains("chargeGameSn")) {
                    chargeGameSn = RegularUtil.getNumber(category);
                } else if (category.contains("rmbnum")) {
                    rmbNum = RegularUtil.getNumber(category);
                } else {
                    logger.error("********* 获取订单信息失败！*********");
                }
            }
            rechargeInfoList.add(new RechargeInfo(zoneId, date, userId, roleId, rmbNum, platId, platName, chargePlatSn, chargeGameSn));
        }else if (content.contains("QHSJGAT_RECHARGE")) {
            // 港澳台充值信息(港澳台charge.log日志文件为空，只能从export/oplog/op/main/目录取的充值日志)
            String[] strings = content.split("\\|");
            roleId = Integer.valueOf(strings[0]);
            date = DateUtil.getFormatDateByMillSecond(strings[1]);
            rmbNum = Integer.valueOf(strings[3]);
            userId = Integer.valueOf(strings[5]);
            // BUG：不能分割类似 白日依山盡|85 的多个字符
            if (strings[12].contains("$")) {
                platId = strings[12].split("\\$")[0];
                platName = strings[12].split("\\$")[1];
            }else {
                platId = strings[13].split("\\$")[0];
                platName = strings[13].split("\\$")[1];
            }
            rechargeInfoList.add((new RechargeInfo(zoneId, date, userId, roleId, rmbNum, platId, platName, chargePlatSn, chargeGameSn)));
        }
        return rechargeInfoList;
    }

    /**
     * 通过文件名截取到 zoneId
     * @param name 文件名
     * @return zoneId
     */
    private static int getZoneIdByFilename(String name) {
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
}
