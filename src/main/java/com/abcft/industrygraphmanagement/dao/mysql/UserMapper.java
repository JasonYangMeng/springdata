package com.abcft.industrygraphmanagement.dao.mysql;

import com.abcft.industrygraphmanagement.model.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Created by YangMeng on 2021/5/11 17:43
 */
@Mapper
public interface UserMapper extends IBaseMapper<UserEntity, String>{

    UserEntity getUserByLoginName(String loginName);
}
