package com.abcft.industrygraphmanagement.dao.mysql;

import com.abcft.industrygraphmanagement.model.entity.SysDictionaryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/3 11:11
 */
@Repository
@Mapper
public interface SysDictionaryMapper extends IBaseMapper<SysDictionaryEntity, String> {

    void addEntryList(List<SysDictionaryEntity> sysDictionaryList);

    void delEntry(String id);

    void updateEntry(SysDictionaryEntity sysDictionary);

    List<SysDictionaryEntity> getSysDictionaryList(String level);

    List<SysDictionaryEntity> getRelationSysDictionaryList(String id);

    List<String> getPostSysDictionaryList(String name);
}
