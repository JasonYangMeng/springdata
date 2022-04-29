package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

/**
 * 指定关系名称为 Supply
 * 公司-公司 供给关系
 *
 * @Author Created by YangMeng on 2021/3/9 11:48
 */
@Data
@RelationshipEntity(value = "Supply")
public class SupplyRelationship {
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
    /**
     * 创建供给关系词条的供给关系词条名称，例如：阿博茨公司 供给 阿里巴巴公司
     */
    private String relationEntryName;

    /**
     * 供给产品id
     */
    private String supplyProductId;
    /**
     * 终端产品id
     */
    private String terminalProductId;
    /**
     * 该供应关系占上游公司产品收入比重
     */
    private String supplyProportion;
    /**
     * 该供应关系占下游公司产品成本比重
     */
    private String purchaseProportion;

    /**
     * 单价
     */
    private String price;

    /**
     * 数量
     */
    private String amount;

    /**
     * 金额
     */
    private String sum;

    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
}
