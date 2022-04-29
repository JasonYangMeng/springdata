package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.mysql.SysDictionaryMapper;
import com.abcft.industrygraphmanagement.model.entity.AllSysDictionaryResultEntity;
import com.abcft.industrygraphmanagement.model.entity.SysDictionaryEntity;
import com.abcft.industrygraphmanagement.service.SysDictionaryService;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/3 11:22
 */
@Service
@Slf4j
public class SysDictionaryServiceImpl implements SysDictionaryService {

    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    /**
     * 查询全量字典数据
     * @return
     */
    @Override
    public List<AllSysDictionaryResultEntity> getAllSysDictionaryList() {
        List<AllSysDictionaryResultEntity> list = new ArrayList<>();
        String level1 = "1";
        String level2 = "2";
        // 一级字典
        List<SysDictionaryEntity> list1 = sysDictionaryMapper.getSysDictionaryList(level1);
        if (CollectionUtils.isEmpty(list1)) {
            log.warn("getAllSysDictionaryList -> warn:{}", "数据字典为空");
            return list;
        }
        // 二级字典
        List<SysDictionaryEntity> list2 = sysDictionaryMapper.getSysDictionaryList(level2);
        if (CollectionUtils.isEmpty(list2)) {
            for (SysDictionaryEntity sysDictionaryEntity : list1) {
                AllSysDictionaryResultEntity entity = new AllSysDictionaryResultEntity();
                entity.setSysDictionaryEntity(sysDictionaryEntity);
                list.add(entity);
            }
            return list;
        }

        // 一级字典 二级字典都不为空
        for (SysDictionaryEntity sysDictionaryEntity : list1) {
            String type1 = sysDictionaryEntity.getType();
            AllSysDictionaryResultEntity entity = new AllSysDictionaryResultEntity();
            entity.setSysDictionaryEntity(sysDictionaryEntity);
            List<SysDictionaryEntity> sysDictionaryList = new ArrayList<>();
            for (SysDictionaryEntity dictionaryEntity : list2) {
                String type2 = dictionaryEntity.getType();
                if (type1.equals(type2)) {
                    sysDictionaryList.add(dictionaryEntity);
                }
            }
            entity.setSysDictionaryList(sysDictionaryList);
            list.add(entity);
        }

        return list;
    }

    /**
     * 添加词条类型
     * @param sysDictionaryList 字典表实体类列表
     */
    @Override
    public void addEntryList(List<SysDictionaryEntity> sysDictionaryList) {
        long createTime = System.currentTimeMillis();
        for (SysDictionaryEntity sysDictionaryEntity : sysDictionaryList) {
            // uuid
            sysDictionaryEntity.setId(UuidUtils.generate());
            // 创建时间
            sysDictionaryEntity.setCreateTime(DateExtUtils.getCurrentDateStr());
        }
        sysDictionaryMapper.addEntryList(sysDictionaryList);
    }

    /**
     * 删除单个词条
     *
     * @param id 词条名称
     */
    @Override
    public void delEntry(String id) {
        sysDictionaryMapper.delEntry(id);
    }

    /**
     * 更新单个词条类型字典
     *
     * @param sysDictionary SysDictionaryEntity SysDictionaryEntity
     */
    @Override
    public void updateEntry(SysDictionaryEntity sysDictionary){
        sysDictionary.setCreateTime(DateExtUtils.getCurrentDateStr());
        sysDictionaryMapper.updateEntry(sysDictionary);
    }

    /**
     * 通过关系字典id 获取关系词条的关系类型
     *
     * @param id 关系字典id
     * @return List<SysDictionaryEntity> 关系类型字典数据列表
     */
    @Override
    public List<SysDictionaryEntity> getRelationSysDictionaryList(String id) {
        return sysDictionaryMapper.getRelationSysDictionaryList(id);
    }

    @Override
    public List<String> getPostSysDictionaryList(String name) {
        return sysDictionaryMapper.getPostSysDictionaryList(name);
    }
}
