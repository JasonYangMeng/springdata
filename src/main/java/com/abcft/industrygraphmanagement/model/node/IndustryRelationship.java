package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * @Author Created by YangMeng on 2021/3/22 11:24
 * 产品-产品产业链 产业链关系
 */
@Data
@RelationshipEntity(value = "Industry")
public class IndustryRelationship {
    @Id
    private String uuid;
    @StartNode
    private ProductEntryNode startNode;
    @EndNode
    private IndustryEntryNode endNode;
}
