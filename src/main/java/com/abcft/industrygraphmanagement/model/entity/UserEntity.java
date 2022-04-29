package com.abcft.industrygraphmanagement.model.entity;

import lombok.Data;

/**
 * @Author Created by YangMeng on 2021/5/11 14:55
 */
@Data
public class UserEntity {
    private String id;
    private String userName;
    private String loginName;
    private String phone;
    private String pwd;
    private String salt;
    /**
     * 头像
     */
    private String image;
    private String createUserId;
    private String createTime;
}
