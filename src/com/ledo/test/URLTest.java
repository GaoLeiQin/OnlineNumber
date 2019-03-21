package com.ledo.test;

import com.ledo.beans.UrlContent;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class URLTest {
    Logger logger = Logger.getLogger(URLTest.class);

    @Test
    public void hashMapTest() {
/*        HashMap<Integer, Integer> map = new HashMap<>(3);
        map.put(1,2);
        map.put(3,4);
        map.put(5,6);
        System.out.println(map.putIfAbsent(7,8));
        System.out.println(map.putIfAbsent(3,3));
        System.out.println(map);*/
    }

    @Test
    public void printInfo() {
        int allSum = 0;
        HashMap<String, URL> urls = init();
        for (Map.Entry<String, URL> info : urls.entrySet()) {
            String serverName = info.getKey();
            URL url = info.getValue();
            int num = getOnlineNumSum(url);
            System.out.println(serverName + " 在线人数总数：" + num);
            allSum += num;
        }

        System.out.println("****************************************");
        System.out.println("《拳皇世界》游戏总的在线人数 ：" + allSum);
    }

    private int getOnlineNumSum(URL url) {
        ArrayList<UrlContent> contents = getUrlContents(url);
        sortByOnlineNum(contents);
        UrlContent maxInfo = contents.size() > 0 ? contents.get(0) : null;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = simpleDateFormat.format(date);
        System.out.println("今天  （" + currentDate + "）" + maxInfo.getMaxOnlineNum() +
                " 在线人数最多的服务器" + maxInfo.getServerName() + " ID：" + maxInfo.getZoneId() + " 最大在线人数: " + maxInfo.getOnlineNum());
        return computeSum(contents);
    }


    private HashMap<String, URL> init() {
        HashMap<String, URL> urlChannel = new HashMap<>(3);
        try {
            urlChannel.put("大陆官服 ", new URL("http://124.243.220.123:29034/ServerStatus"));
            urlChannel.put("大陆硬核与混服", new URL("http://124.243.220.124:29034/ServerStatus"));
            urlChannel.put("港澳台", new URL("http://220.229.229.38:29034/ServerStatus"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return urlChannel;
    }

    private ArrayList<UrlContent> getUrlContents(URL url) {
        BufferedReader br = null;
        InputStreamReader isr = null;
        ArrayList<UrlContent> contents = new ArrayList<>();

        try {
            //通过URL的openStrean方法获取URL对象所表示的自愿字节输入流
            isr = new InputStreamReader(url.openStream(), "utf-8");

            //为字符输入流添加缓冲
            br = new BufferedReader(isr);
            String data = br.readLine();

            while (data != null) {
                String[] info = data.split("##");
                if (info.length > 4) {
                    String channelName = info[0];
                    int id = Integer.valueOf(info[1].trim());
                    String serverName = info[2].trim();
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
                if (isr == null) {
                    isr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return contents;
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
        // 依据在线人数对网页内容进行排序
        Collections.sort(contents, new Comparator<UrlContent>() {
            @Override
            public int compare(UrlContent o1, UrlContent o2) {
                if (o1.getOnlineNum() < o2.getOnlineNum()) {
                    return 1;
                } else if (o1.getOnlineNum() == o2.getOnlineNum()) {
                    if (o1.getZoneId() > o2.getZoneId()) {
                        return 1;
                    } else {
                        return -2;
                    }
                }
                return -2;
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
