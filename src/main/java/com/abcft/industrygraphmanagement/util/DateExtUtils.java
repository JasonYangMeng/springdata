package com.abcft.industrygraphmanagement.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Created by YangMeng on 2021/5/13 15:24
 * 时间处理
 */
public class DateExtUtils {

    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String shortDateFormat = "yyyy-MM-dd";

    public static String getCurrentDateStr() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
    }

}
