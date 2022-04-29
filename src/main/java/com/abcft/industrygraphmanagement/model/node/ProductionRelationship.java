package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

/**
 * 公司->产品 生产关系
 * 指定关系名称为Production
 *
 * @Author Created by YangMeng on 2021/3/4 14:09
 */
@Data
@RelationshipEntity(type = "Production")
public class ProductionRelationship {

    @Id
    private String uuid;
    @StartNode
    private CompanyEntryNode startNode;

    @EndNode
    private ProductEntryNode endNode;

    /**
     * 创建关系词条的关系词条名称，例如：阿博茨公司 生产 产业链图谱
     */
    private String relationEntryName;

    /**
     * 名称
     */
    private String name;

    /**
     * 别名
     */
    private String aliasName;

    /**
     * 简介
     */
    private String introduction;
    /**
     * 图片路径
     */
    private String imagePath;

    /**
     * 收入占比 / 占主营收入比
     */
    private String incomeProportion;
    /**
     * 产品总收入
     */
    private String productTotalIncome;
    /**
     * 收入同比增长
     */
    private String revenueGrowth;
    /**
     * 市场价格 / 产品单价
     */
    private String productPrice;

    /**
     * 市场份额占比
     */
    private String marketShares;

    /**
     * 毛利率
     */
    private String productGross;
    /**
     * 产能
     */
    private String capacity;
    /**
     * 行业产能
     */
    private String industryCapacity;
    /**
     * 产能利用率
     */
    private String capacityRatio;
    /**
     * 行业产能占比
     */
    private String capacityProportion;

}
