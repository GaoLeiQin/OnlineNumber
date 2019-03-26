package com.ledo.manager;

import org.apache.log4j.Logger;

/**
 * manager 基类
 * @author qgl
 * @date 2018/11/16
 */
public class BaseManager {
    public static Logger logger = Logger.getLogger(BaseManager.class);

    public static BaseManager instance = new BaseManager();

    public static BaseManager getInstance() {
        return instance;
    }

}
