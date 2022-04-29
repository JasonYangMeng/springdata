package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * @Author Created by YangMeng on 2021/3/24 10:12
 * 产品-生产工艺 生产工艺关系
 */
@Data
@RelationshipEntity(value = "Artwork")
public class ArtworkRelationship {
    @Id
    private String uuid;

    @StartNode
    private ProductEntryNode startNode;
    @EndNode
    private ArtworkEntryNode endNode;
}
