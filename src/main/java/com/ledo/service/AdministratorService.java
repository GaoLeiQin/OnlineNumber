package com.ledo.service;

import com.ledo.beans.*;
import com.ledo.manager.FileManager;
import com.ledo.manager.URLManager;
import com.ledo.dao.*;
import com.ledo.task.AllTask;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ledo.common.FileConstant.SERVER_OPENDAYS_PATH;
import static com.ledo.common.ServerConstant.GAT;
import static com.ledo.common.FileConstant.RECHARGE_LOG_PATH;
import static com.ledo.common.FileConstant.ZONE_OPT_PATH;

/**
 * 管理员service 具体实现
 * @author qgl
 * @date 2018/10/24
 */
@Service("IAdministratorService")
public class AdministratorService implements IAdministratorService{
    Logger logger = Logger.getLogger(AdministratorService.class);

    @Autowired
    @Qualifier("IUrlContent")
    private IUrlContent urlContentDao;

    @Autowired
    @Qualifier("IAdministrator")
    private IAdministrator administratorDao;

    @Autowired
    @Qualifier("IAllServer")
    private IAllServer allServerDao;

    @Autowired
    @Qualifier("IRecharge")
    private IRecharge rechargeDao;

    @Autowired
    @Qualifier("IGuest")
    private IGuest guestDao;

    @Override
    public void addGuestInfo(HttpServletRequest request, String userName) {
        guestDao.insertGuestInfo(URLManager.increaseGuestInfo(request, userName));
    }

    @Override
    public ArrayList<GuestInfo> queryGuestInfo() {
        return guestDao.queryGuestInfo();
    }

    @Override
    public ArrayList<GuestInfo> queryGuestInfoByCondition(GuestInfo guestInfo) {
        return guestDao.queryGuestInfoByCondition(guestInfo);
    }

    @Override
    public Integer queryGuestInfoCountByCondition(GuestInfo guestInfo) {
        return guestDao.queryGuestInfoCountByCondition(guestInfo);
    }

    @Override
    public void openAutoUpdateTask() {
        AllTask.getInstance().openAutoUpdateTask(administratorDao, urlContentDao);
    }

    @Override
    public ArrayList<UrlContent> queryUrlContents() {
        return urlContentDao.queryUrlContents();
    }

    // 更新网页内容
    @Override
    public void updateUrlContent() {
        urlContentDao.deleteUrlContent();
        boolean isGAT = false;
        for (Map.Entry<String, URL> urls : URLManager.getInstance().getUrl().entrySet()) {
            if (GAT.equals(urls.getKey())) {
                isGAT = true;
            }
            for (UrlContent urlContent : URLManager.getInstance().getUrlContents(urls.getValue())) {
                if (isGAT) {
                    urlContent.setChannel("GAT_" + urlContent.getChannel());
                }
                urlContentDao.insertUrlContent(urlContent);
            }
        }

        logger.warn("QHSJSERVERINFO " + urlContentDao.queryUrlContents());
        long now = System.currentTimeMillis();
    }

    // 向历史数据中添加当前服务器在线信息
    @Override
    public void addServerInfo() {
        boolean isNull = urlContentDao.queryOfficialSum() == null || urlContentDao.queryMixSum() == null ||
                urlContentDao.queryGatSum() == null || urlContentDao.queryAllSum() == null;
        if (isNull) {
            logger.error(" #$# 删除数据库");
            // 启动线程
            try {
                // 等待数据更新
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ServerHistoryInfo server = new ServerHistoryInfo(FileManager.getNowFormatDate(), urlContentDao.queryOfficialSum(), urlContentDao.queryMixSum(),
                urlContentDao.queryGatSum(), urlContentDao.queryAllSum());

        if (urlContentDao.queryOfficialSum() == null || urlContentDao.queryMixSum() == null ||
                urlContentDao.queryGatSum() == null || urlContentDao.queryAllSum() == null) {
            logger.error(" &$& 网页访问为空的数据：" + server);
        }
        administratorDao.insertServerInfo(server);
    }

    @Override
    public ArrayList<ServerHistoryInfo> referServerHistoryInfoByLimit25() {
        return administratorDao.queryServerHistoryInfoByLimit25();
    }

    /**********************************  更新Linux服务器相关信息 ************************************************/
    @Override
    public ArrayList<AllServerInfo> referLinuxServerInfo() {
        return allServerDao.queryAllServerInfo();
    }

    @Override
    public void deleteLinuxServerInfo() {
        administratorDao.deleteLinuxServerInfo();
    }

    @Override
    public void updateLinuxServerInfo() {
        HashMap<Integer, String> daluZoneNameAndId = this.getZoneIdAndName(RECHARGE_LOG_PATH);
        HashMap<Integer, String> gatZoneNameAndId = this.getGatZoneIdAndName(RECHARGE_LOG_PATH);
        HashMap<String, String> zoneOpt = this.getZoneOpt(ZONE_OPT_PATH);
        HashMap<Integer, String> daluOpenDays = this.getZoneIdAndOpenDays(SERVER_OPENDAYS_PATH);
        HashMap<Integer, String> gatOpenDays = this.getGatZoneIdAndOpenDays(SERVER_OPENDAYS_PATH);
        for (UrlContent content : urlContentDao.queryUrlContents()) {
            // 更新服务器信息时 区分港澳台与大陆的差异
            if (content.getChannel().contains("GAT")) {
                addData(gatZoneNameAndId, zoneOpt, content, gatOpenDays);
            } else {
                addData(daluZoneNameAndId, zoneOpt, content, daluOpenDays);
            }
        }
    }

    /**
     * 保存Linux服务器信息
     * @param zoneNameAndId
     * @param zoneOpt
     * @param content
     * @param serverOpenDays
     */
    public void addData(HashMap<Integer, String> zoneNameAndId, HashMap<String, String> zoneOpt, UrlContent content, HashMap<Integer, String> serverOpenDays) {
        String channel = content.getChannel();
        int zoneId = content.getZoneId();
        String serverName = content.getServerName();

        int optOrId = 0;
        String ip = null;
        String hostName = null;
        String openTime = null;
        int openDays = 0;

        if (zoneNameAndId.containsKey(zoneId)){
            hostName = zoneNameAndId.get(zoneId);
            if (serverOpenDays.containsKey(zoneId)) {
                openTime = serverOpenDays.get(zoneId);
                openDays = FileManager.getServerOpenDays(openTime);
            }
            if (zoneOpt.containsKey(hostName)) {
                String[] idAndIp = zoneOpt.get(hostName).split("=");
                if (idAndIp.length > 1) {
                    optOrId = Integer.valueOf(idAndIp[0]);
                    ip = idAndIp[1];
                }
                // 保存Linux服务器相关数据
                administratorDao.insertLinuxServerInfo(new AllServerInfo(channel, zoneId, serverName, optOrId, ip, hostName, content.getOnlineNum(), openTime, openDays));
            }else {
                System.out.println("更新Linux信息失败:" + zoneId + " " + hostName);
            }
        }else {
            System.out.println("新开服:" + zoneId + " " + serverName);
            administratorDao.insertLinuxServerInfo(new AllServerInfo(channel, zoneId, serverName, optOrId, "新开服", "Opt Or ID、IP、HostName待更新", content.getOnlineNum(), openTime, openDays));
        }
    }

    /**
     * 获取大陆Linux服务器ID与对应的name
     * @param directoryName
     */
    public HashMap<Integer, String> getZoneIdAndName(String directoryName) {
        File[] files = new File(directoryName).listFiles();
        if (files == null) {
            return null;
        }

        HashMap<Integer, String> zoneNameAndId = new HashMap<>(128);
        for (File file : files) {
            String fileName = file.getName().split("\\.")[0];
            // 区分是否是测试或提审的数据
            boolean isBeta = fileName.contains("test") || fileName.contains("tishen") || fileName.contains("null");

            if (!fileName.contains("gat")) {
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
                    int optOrId = FileManager.getNumber(content.substring(0,5));
                    String ip = content.substring(5,20).trim();
                    int index = content.indexOf("[jp_admin]");
                    String serverName = content.substring(index - 32, index).trim();
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
    }

    /**
     * 获取服务器的开服时间
     * @param fileName
     */
    public HashMap<Integer, String> getZoneIdAndOpenDays(String fileName) {
        HashMap<Integer, String> serverOpenDays = new HashMap<>(64);
        BufferedReader br = null;
        try {
            // 读取服务器的开服时间
            br = new BufferedReader(new FileReader(fileName));
            String str = null;

            while ((str = br.readLine()) != null) {
                if (str == null || str.length() < 25) {
                    continue;
                }
                int zoneId = FileManager.getNumber(str.substring(0, 5));
                String openDays = str.substring(5, 25).trim();
                serverOpenDays.putIfAbsent(zoneId, openDays);
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

        return serverOpenDays;
    }

    /**
     * 获取港澳台服务器的开服时间
     * @param fileName
     */
    public HashMap<Integer, String> getGatZoneIdAndOpenDays(String fileName) {
        HashMap<Integer, String> gatOpenDays = new HashMap<>(64);
        BufferedReader br = null;
        try {
            // 读取服务器的开服时间
            br = new BufferedReader(new FileReader(fileName));
            String str = null;

            while ((str = br.readLine()) != null) {
                if (str == null || str.length() < 25) {
                    continue;
                }
                if (str.contains("GAT")) {
                    int zoneId = FileManager.getNumber(str.substring(0, 5));
                    String openDays = str.substring(5, 25).trim();
                    gatOpenDays.putIfAbsent(zoneId, openDays);
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

        return gatOpenDays;
    }

    /**********************************  更新充值数据 ************************************************/
    @Override
    public ArrayList<RechargeInfo> referRechargeInfo() {
        return rechargeDao.queryRechargeInfo();
    }

    @Override
    public void deleteRechargeInfo() {
        administratorDao.deleteRechargeInfo();
    }

    @Override
    public void updateRechargeInfo() {
        getDirectory(RECHARGE_LOG_PATH);
    }

    /**
     * 读取目标文件夹中的全部文件名
     * @param directoryName
     */
    private void getDirectory(String directoryName) {
        File[] files = new File(directoryName).listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            String fileName = file.getAbsolutePath();
            boolean isBeta = fileName.contains("test") || fileName.contains("tishen") || fileName.contains("null");
            if (!isBeta) {
                getFiles(fileName);
                break;
            }else {
                logger.error(fileName);
            }
        }

    }

    /**
     * 读取文件中每一行的内容
     * @param fileName
     */
    private void getFiles(String fileName) {
        BufferedReader br = null;
        try {
            // 读取充值数据
            br = new BufferedReader(new FileReader(fileName));
            String str = null;

            int count = 0;
            while ((str = br.readLine()) != null) {
                getRachargeInfo(str, FileManager.getZoneIdByFilename(fileName));
                if (count++ > 30) {
                    break;
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
        logger.info(fileName);
    }

    /**
     * 分类提取充值相关信息
     * @param content 文件的每一行内容
     * @param zoneId
     */
    private void getRachargeInfo(String content, int zoneId) {
        String date = null;
        int userId = 0;
        int roleId = 0;
        int rmbNum = 0;
        String platId = null;
        String platName = null;
        String chargePlatSn = "-";
        int chargeGameSn = 0;

        if (content.contains("ordersuccess")) {
            String[] strings = content.split(",");
            for (String category : strings) {
                if (category.contains("roleid")) {
                    date = category.substring(0, category.indexOf("INFO") - 1);
                    roleId = Integer.valueOf(category.substring(category.indexOf("roleid") + 7));

                } else if (category.contains("platUid")) {
                    if (category.split("\\$").length > 1) {
                        platId = String.valueOf(FileManager.getNumber(category.split("\\$")[0]));
                        platName = category.split("\\$")[1];
                    }

                } else if (category.contains("userid")) {
                    userId = FileManager.getNumber(category);

                } else if (category.contains("chargePlatSn")) {
                    if (category.split(":").length > 1) {
                        try {
                            chargePlatSn = new String(category.split(":")[1].getBytes("gbk"),"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                } else if (category.contains("chargeGameSn")) {
                    chargeGameSn = FileManager.getNumber(category);

                } else if (category.contains("rmbnum")) {
                    rmbNum = FileManager.getNumber(category);
                } else {
                    logger.error("********* 获取订单信息失败！*********");
                }
            }
            // 保存大陆充值数据
            administratorDao.insertRechargeInfo(new RechargeInfo(zoneId, date, userId, roleId, rmbNum, platId, platName, chargePlatSn, chargeGameSn));
        }else if (content.contains("QHSJGAT_RECHARGE")) {
            String[] strings = content.split("\\|");
            roleId = Integer.valueOf(strings[0]);
            date = FileManager.getFormatDateByMillSecond(strings[1]);
            rmbNum = Integer.valueOf(strings[3]);
            userId = Integer.valueOf(strings[5]);
            // | 不能分割 白日依山盡|85 相似多个字符（未知BUG）
            if (strings[12].contains("$")) {
                platId = strings[12].split("\\$")[0];
                platName = strings[12].split("\\$")[1];
            }else {
                platId = strings[13].split("\\$")[0];
                platName = strings[13].split("\\$")[1];
            }
            administratorDao.insertRechargeInfo(new RechargeInfo(zoneId, date, userId, roleId, rmbNum, platId, platName, chargePlatSn, chargeGameSn));
        }

        // 保存订单数据
    }

    /**********************************  定时任务 ************************************************/
    public class SaveServerInfoTask implements Runnable{

        @Override
        public void run() {
            this.insertUrlContent();
        }

        private void insertUrlContent() {
            ServerHistoryInfo server = new ServerHistoryInfo(FileManager.getNowFormatDate(), urlContentDao.queryOfficialSum(), urlContentDao.queryMixSum(),
                    urlContentDao.queryGatSum(), urlContentDao.queryAllSum());
            administratorDao.insertServerInfo(server);
        }
    }
}
