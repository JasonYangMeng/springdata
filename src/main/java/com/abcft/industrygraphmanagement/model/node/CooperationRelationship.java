package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * @Author Created by YangMeng on 2021/3/24 10:13
 * 产品-协作图 生产协作关系
 */
@Data
@RelationshipEntity(value = "Cooperation")
public class CooperationRelationship {
    @Id
    private String uuid;

    @StartNode
    private ProductEntryNode startNode;
    @EndNode
    private CooperationEntryNode endNode;
}
