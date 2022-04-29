package com.abcft.industrygraphmanagement.util;


import org.springframework.util.DigestUtils;

/**
 * @Author Created by YangMeng on 2021/5/12 10:30
 */
public class Md5Utils {
    /**
     * 获取md5加密
     *
     * @param pwd
     * @param salt
     * @return
     */
    public static String getMd5(String pwd, String salt) {
        return DigestUtils.md5DigestAsHex((pwd + salt).getBytes());
    }

}
