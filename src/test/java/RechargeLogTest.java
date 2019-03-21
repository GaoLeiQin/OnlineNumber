import com.ledo.manager.FileManager;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 充值日志文件处理
 * @author qgl
 * @date 2018/9/28
 */
public class RechargeLogTest {
    Logger logger = Logger.getLogger(RechargeLogTest.class);

    @Test
    public void test() {
        String directoryName = "E:\\papers\\companyfile\\log\\Recharge_log";
        System.out.println("测试充值信息！");
//        getDirectory(directoryName);
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
            System.out.println("******** ZoneID: "+getZoneIdByFilename(fileName));
            boolean isBeta = fileName.contains("test") || fileName.contains("tishen") || fileName.contains("null");
/*            System.out.println("########## " + fileName + " ##########");
            if (!isBeta && fileName.contains("gat")) {
                getFiles(fileName, true);
            }else if (!isBeta) {
                getFiles(fileName, false);
            }else {
                logger.error(fileName);
            }*/
        }

    }

    /**
     * 读取文件中每一行的内容
     * @param fileName
     */
    private void getFiles(String fileName, boolean isGat) {
        BufferedReader br = null;
        try {
            // 读取充值数据
            br = new BufferedReader(new FileReader(fileName));
            String str = null;

            while ((str = br.readLine()) != null) {
                if (isGat) {
                    getGatContent(str);
                }else {
                    getContent(str);
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
     * 分别获取大陆订单相关信息
     * @param content
     */
    private void getContent(String content) {
        String date = null;
        int userId = 0;
        int roleId = 0;
        int rmbNum = 0;
        String platId = null;
        String platName = null;
        String chargePlatSn = null;
        int chargeGameSn = 0;

        if (content.contains("ordersuccess")) {
            String[] strings = content.split(",");
            for (String category : strings) {
                if (category.contains("roleid")) {
                    date = category.substring(0, category.indexOf("INFO") - 1);
                    roleId = Integer.valueOf(category.substring(category.indexOf("roleid") + 7));

                }else if (category.contains("platUid")) {
                    if (category.split("\\$").length > 1) {
                        platId = category.split("\\$")[0];
                        platName = category.split("\\$")[1];
                    }

                }else if (category.contains("userid")) {
                    userId = FileManager.getNumber(category);

                } else if (category.contains("chargePlatSn")) {
                    if (category.split(":").length > 1) {
                        chargePlatSn = category.split(":")[1];
                    }

                } else if (category.contains("chargeGameSn")) {
                    chargeGameSn = FileManager.getNumber(category);

                }else if (category.contains("rmbnum")) {
                    rmbNum = FileManager.getNumber(category);
                }else {
                    logger.error("********* 获取订单信息失败！*********");
                }
            }

            // 保存订单数据
            System.out.println(date + " " + userId + " " + roleId + " " + rmbNum + " " +
                    platId + " " + platName + " " + chargePlatSn + " " + chargeGameSn);
        }
    }

    /**
     * 获取港澳台订单相关信息
     * @param content
     */
    private void getGatContent(String content) {
        String date = null;
        int userId = 0;
        int roleId = 0;
        int rmbNum = 0;
        String platId = null;
        String platName = null;
        String chargePlatSn = null;
        int chargeGameSn = 0;

        if (content.contains("QHSJGAT_RECHARGE")) {
            String[] strings = content.split("\\|");
            roleId = Integer.valueOf(strings[0]);
            date = getDate(strings[1]);
            rmbNum = Integer.valueOf(strings[3]);
            userId = Integer.valueOf(strings[5]);
            platName = strings[12];
            platId = strings[13].split("\\$")[0];

            // 保存订单数据
            System.out.println(" ******* " + date + " " + userId + " " + roleId + " " + rmbNum + " " +
                    platId + " " + platName + " " + chargePlatSn + " " + chargeGameSn);
        }
    }

    private String getDate(String timeString) {
        Date date = new Date(Long.valueOf(timeString));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    private int getZoneIdByFilename(String fileName) {
        String[] names = fileName.substring(39).split("\\.");
        for (String name : names) {
            System.err.println(name);
        }
        return Integer.valueOf(names[0].split("=")[0]);
    }
}
