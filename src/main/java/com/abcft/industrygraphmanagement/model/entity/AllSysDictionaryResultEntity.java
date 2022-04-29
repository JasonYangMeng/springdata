package com.abcft.industrygraphmanagement.model.entity;

import lombok.Data;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/16
 */
@Data
public class AllSysDictionaryResultEntity {

    private SysDictionaryEntity sysDictionaryEntity;

    private List<SysDictionaryEntity> sysDictionaryList;
}
