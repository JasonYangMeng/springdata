package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.condition.SysCollectCondition;
import com.abcft.industrygraphmanagement.model.entity.SysCollectSettingEntity;
import com.abcft.industrygraphmanagement.model.result.Paged;

/**
 * @Author Created by YangMeng on 2021/5/12 15:10
 */
public interface SysCollectService {
    /**
     * 添加
     *
     * @param sysCollectSettingEntity
     */
    void insert(SysCollectSettingEntity sysCollectSettingEntity) throws Exception;

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
    Paged<SysCollectSettingEntity> getPagedListByCondition(SysCollectCondition sysCollectCondition);
}
