package com.abcft.industrygraphmanagement.dao.mysql;

import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/10
 */
@Repository
@Mapper
public interface EntryLinkMapper {

    List<EntryLinkEntity> queryEntryLinkRecentlyList(String createUserId);

    Integer addEntryLinkList(List<EntryLinkEntity> entryLinkList);

    void deleteEntryLinkList(String entryId);

    List<EntryLinkEntity> queryEntryLinkInnerList(String var);

    List<EntryLinkEntity> queryEntryLinkList(String entryId);

    void addEntryLink(EntryLinkEntity entryLinkEntity);

    void updateEntryLink(EntryLinkEntity entryLinkEntity);
}
