package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.entity.UserEntity;

/**
 * @Author Created by YangMeng on 2021/5/11 16:11
 */
public interface UserService {
    /**
     * 获取登录人信息
     *
     * @param loginName
     * @return
     */
    UserEntity getUserByLoginName(String loginName);

    /**
     * 通过id登录
     *
     * @param id
     * @return
     */
    UserEntity getUserById(String id);
}
