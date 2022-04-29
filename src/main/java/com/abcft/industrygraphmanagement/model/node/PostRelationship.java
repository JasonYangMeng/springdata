package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * 人物->公司  任职关系
 *
 * @author WangZhiZhou
 * @date 2021/3/19
 */
@Data
@RelationshipEntity(value = "Post")
public class PostRelationship {
    @Id
    private String uuid;
    /**
     * 开始节点
     */
    @StartNode
    private PersonEntryNode startNode;
    /**
     * 结束节点
     */
    @EndNode
    private CompanyEntryNode endNode;
    /**
     * 创建关系词条的关系词条名称
     */
    private String relationEntryName;
    /**
     * 职务
     */
    private String post;
    /**
     * 持股比例 / 股权比例
     */
    private String shareholdingRatio;

    /**
     * 期末持股数(股)
     */
    private String shareHoldNum;

    /**
     * 期末参考持股市值（元）
     */
    private String shareHoldRefVal;
}
