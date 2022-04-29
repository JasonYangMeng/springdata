package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/10
 */
public interface EntryInfoSourceService {

    Integer addEntryInfoSource(List<EntryInfoSourceEntity> entryInfoSourceEntityList);

    Integer addEntryInfoSource1(EntryInfoSourceEntity entryInfoSourceEntity);

    Integer deleteEntryInfoSource(String entryId);

    /**
     * 根据entryId查询数据
     *
     * @param entryId 词条id
     * @return List<EntryInfoSourceEntity>词条信息源对象列表
     */
    List<EntryInfoSourceEntity> queryAllByEntryId(String entryId);

    /**
     * 单条更新词条来源数据
     *
     * @param entryInfoSourceEntity
     */
    void updateEntryInfoSource1(EntryInfoSourceEntity entryInfoSourceEntity);
}
