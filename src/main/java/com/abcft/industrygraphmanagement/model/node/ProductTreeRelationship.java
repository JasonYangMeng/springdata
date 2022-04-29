package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * @Author Created by YangMeng on 2021/3/23 17:45
 * 产品-产品族谱 产品族谱关系
 */
@Data
@RelationshipEntity(value = "ProductTree")
public class ProductTreeRelationship {
    @Id
    private String uuid;

    @StartNode
    private ProductTreeEntryNode startNode;
    @EndNode
    private ProductEntryNode endNode;
}
