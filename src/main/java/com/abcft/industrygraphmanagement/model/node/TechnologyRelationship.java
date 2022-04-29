package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * 产品->产品  加工工艺关系
 *
 * @author WangZhiZhou
 * @date 2021/3/19
 */
@Data
@RelationshipEntity(value = "Technology")
public class TechnologyRelationship {
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
     * 创建关系词条的关系词条名称，例如：阿博茨公司 生产 产业链图谱
     */
    private String relationEntryName;
}
