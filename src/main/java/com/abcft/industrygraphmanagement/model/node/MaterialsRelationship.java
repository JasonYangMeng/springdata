package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * 产品种类-产品种类 原材料关系
 *
 * @Author Created by YangMeng on 2021/3/15 14:29
 */
@Data
@RelationshipEntity(type = "Materials")
public class MaterialsRelationship {
    @Id
    private String uuid;
    /**
     * 开始节点
     */
    @StartNode
    private ProductEntryNode startNode;
    /**
     * 结束节点
     */
    @EndNode
    private ProductEntryNode endNode;
    /**
     * 创建关系词条的关系词条名称
     */
    private String relationEntryName;

    /**
     * 原材料成本占比
     */
    private String costProportion;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
}
