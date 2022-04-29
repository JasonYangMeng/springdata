package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.mysql.UserMapper;
import com.abcft.industrygraphmanagement.model.entity.UserEntity;
import com.abcft.industrygraphmanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Created by YangMeng on 2021/5/11 16:23
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取登录人信息
     *
     * @param loginName
     * @return
     */
    @Override
    public UserEntity getUserByLoginName(String loginName) {
        return userMapper.getUserByLoginName(loginName);
    }

    /**
     * 通过id登录
     *
     * @param id
     * @return
     */
    @Override
    public UserEntity getUserById(String id) {
        return userMapper.getOne(id);
    }
}
