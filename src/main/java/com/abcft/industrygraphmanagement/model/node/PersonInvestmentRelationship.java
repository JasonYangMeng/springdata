package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * 人物->公司  投资关系
 *
 * @author WangZhiZhou
 * @date 2021/3/31
 */
@Data
@RelationshipEntity(value = "PersonInvestment")
public class PersonInvestmentRelationship {
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
     * 投资占比
     */
    private String investmentProportion;

    /**
     * 方向 不变：0  增加：1  减少：2
     */
    private String shareHoldStatus;

    /**
     * 期末参考市值（亿元）
     */
    private String termEndRefVal;

    /**
     * 持股数量（股）
     */
    private String shareHoldNum;

    /**
     * 持股数量变动（股）
     */
    private String shareHoldNumChange;

    /**
     * 占总股本比例（%）
     */
    private String proportionInTotal;
}
