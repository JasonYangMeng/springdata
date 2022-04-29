package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/10
 */
public interface EntryLinkService {

    List<EntryLinkEntity> queryEntryLinkRecentlyList(String createUserId);

    List<EntryLinkEntity> queryEntryLinkInnerList(String var);

    /**
     * 精确查询词条链接
     *
     * @param entryId 入参
     * @return List<EntryLinkEntity> 词条链接列表
     */
    List<EntryLinkEntity> queryEntryLinkList(String entryId);

    Integer addEntryLinkList(List<EntryLinkEntity> entryLinkList);

    void deleteEntryLinkList(String entryId);

    void addEntryLink(EntryLinkEntity entryLinkEntity);

    void updateEntryLink(EntryLinkEntity entryLinkEntity);
}
