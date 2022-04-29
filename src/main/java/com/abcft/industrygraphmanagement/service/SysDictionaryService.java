package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.entity.AllSysDictionaryResultEntity;
import com.abcft.industrygraphmanagement.model.entity.SysDictionaryEntity;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/3 11:22
 */

public interface SysDictionaryService {

    List<AllSysDictionaryResultEntity> getAllSysDictionaryList();


    /**
     * 批量添加词条类型
     *
     * @param sysDictionaryList 字典表实体类列表
     */
    void addEntryList(List<SysDictionaryEntity> sysDictionaryList);

    /**
     * 删除单个词条类型
     *
     * @param id 词条名称
     */
    void delEntry(String id);

    /**
     * 更新单个词条类型字典
     *
     * @param sysDictionary SysDictionaryEntity
     */
    void updateEntry(SysDictionaryEntity sysDictionary);

    /**
     * 通过关系字典id 获取关系词条的关系类型
     *
     * @param id 关系字典id
     * @return List<SysDictionaryEntity> 关系类型字典数据列表
     */
    List<SysDictionaryEntity> getRelationSysDictionaryList(String id);

    List<String> getPostSysDictionaryList(String name);
}
