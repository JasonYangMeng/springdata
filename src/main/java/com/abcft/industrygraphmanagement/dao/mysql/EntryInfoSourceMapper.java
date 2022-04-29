package com.abcft.industrygraphmanagement.dao.mysql;

import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/10
 */
@Repository
@Mapper
public interface EntryInfoSourceMapper {

    Integer addEntryInfoSource(List<EntryInfoSourceEntity> entryInfoSourceList);

    Integer deleteEntryInfoSource(String entryId);

    List<EntryInfoSourceEntity> queryAllByEntryId(String entryId);

    Integer addEntryInfoSource1(EntryInfoSourceEntity entryInfoSourceEntity);

    void updateEntryInfoSource1(EntryInfoSourceEntity entryInfoSourceEntity);
}
