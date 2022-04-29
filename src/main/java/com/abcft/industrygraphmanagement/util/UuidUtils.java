package com.abcft.industrygraphmanagement.util;

import java.util.UUID;

/**
 * uuid 转化
 */
public class UuidUtils {

    public static String generate() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public static String generate(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().replaceAll("-", "");
    }

}
