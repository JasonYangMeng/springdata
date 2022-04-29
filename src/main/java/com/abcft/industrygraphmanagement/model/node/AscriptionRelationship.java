package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * 产品-产品 归属关系
 * 指定关系名称为 Ascription
 *
 * @author WangZhiZhou
 * @date 2021/3/11
 */
@Data
@RelationshipEntity(value = "Ascription")
public class AscriptionRelationship {
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
}
