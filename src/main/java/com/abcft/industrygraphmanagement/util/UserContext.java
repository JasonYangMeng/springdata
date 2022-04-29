package com.abcft.industrygraphmanagement.util;

import com.abcft.industrygraphmanagement.model.entity.UserEntity;

/**
 * @Author Created by YangMeng on 2021/5/12 15:27
 * 用户上下文
 */
public class UserContext {

    private static final ThreadLocal<UserEntity> user = new ThreadLocal<UserEntity>();

    public static void add(UserEntity userEntity) {
        user.set(userEntity);
    }

    public static void remove() {
        user.remove();
    }

    /**
     * @return 当前登录用户id
     */
    public static String getCurrentUserId() {
        UserEntity userEntity = getCurrentUser();
        if (userEntity != null) {
            return userEntity.getId();
        }
        return "";
    }

    /**
     * 返回当前用户
     *
     * @return
     */
    public static UserEntity getCurrentUser() {
        return user.get();
    }
}
