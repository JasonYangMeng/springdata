package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.condition.SysCollectCondition;
import com.abcft.industrygraphmanagement.model.entity.SysSubscribeSettingEntity;
import com.abcft.industrygraphmanagement.model.result.Paged;

/**
 * @Author Created by YangMeng on 2021/5/12 15:11
 */
public interface SysSubscribeService {
    /**
     * 添加
     *
     * @param sysSubscribeSettingEntity
     */
    void insert(SysSubscribeSettingEntity sysSubscribeSettingEntity) throws Exception;

    /**
     * 删除数据
     *
     * @param entryId
     */
    void delete(String entryId) throws Exception;

    /**
     * 分页获取列表
     *
     * @param sysCollectCondition
     * @return
     */
    Paged<SysSubscribeSettingEntity> getPagedListByCondition(SysCollectCondition sysCollectCondition);
}
