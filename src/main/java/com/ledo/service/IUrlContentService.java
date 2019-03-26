package com.ledo.service;

import com.ledo.beans.UrlContent;

import java.util.ArrayList;
import java.util.HashMap;

public interface IUrlContentService {
    /** 查询网页内容 */
    ArrayList<UrlContent> queryUrlContents();
    /** 更新网页内容 */
    void updateUrlContent();
    /** 将网页内容分为 官服、硬核混服、安卓混服、港澳台四部分 */
    HashMap<String, ArrayList<UrlContent>> getURLContentsMapByCondition(ArrayList<UrlContent> urlContents);

}
