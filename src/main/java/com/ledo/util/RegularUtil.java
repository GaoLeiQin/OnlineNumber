package com.ledo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对Object进行正则处理的工具类
 * @author qgl
 * @date 2018/11/17
 */
public class RegularUtil {
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
}
