package com.ledo.dao;

import com.ledo.beans.UrlContent;

import java.util.ArrayList;

public interface IUrlContent {
    void insertUrlContent(UrlContent urlContent);
    void deleteUrlContent();
    ArrayList<UrlContent> queryUrlContents();

    /** 查询各个渠道在线人数的总和 */
    Integer queryOfficialSum();
    Integer queryMixSum();
    Integer queryGatSum();
    Integer queryAllSum();
}
