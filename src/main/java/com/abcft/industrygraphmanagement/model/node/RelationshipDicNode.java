package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @Author Created by YangMeng on 2021/4/8 10:37
 * 关系同义词 英文对照
 */
@Data
@NodeEntity(label = "RelationshipDic")
public class RelationshipDicNode {
    /**
     * 主键
     */
    @Id
    private String uuid;

    /**
     * 关系名称
     */
    private String nameEn;
    /**
     * 中文名称集合
     */
    private String name;
    /**
     * 方向 1：上游 2下游
     */
    private String direction;
}
