package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * 公司->公司  投资关系
 *
 * @author WangZhiZhou
 * @date 2021/3/19
 */
@Data
@RelationshipEntity(value = "Investment")
public class InvestmentRelationship {
    @Id
    private String uuid;
    /**
     * 开始节点
     */
    @StartNode
    private CompanyEntryNode startNode;
    /**
     * 结束节点
     */
    @EndNode
    private CompanyEntryNode endNode;
}
