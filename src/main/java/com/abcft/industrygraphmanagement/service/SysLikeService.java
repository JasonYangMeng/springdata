package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.entity.SysLikeEntity;

/**
 * @Author Created by YangMeng on 2021/5/17 10:44
 */
public interface SysLikeService {
    /**
     * 添加
     *
     * @param sysLikeEntity
     */
    int insert(SysLikeEntity sysLikeEntity) throws Exception;

    /**
     * 删除数据
     *
     * @param entryId
     */
    int delete(String entryId) throws Exception;
}
