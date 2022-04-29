package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * @Author Created by YangMeng on 2021/3/20 14:52
 * 产品-产品 公用编辑类
 */
@Data
@QueryResult
public class ProductToProductDto {

    private String uuid;
    /**
     * 开始节点id
     */
    private String startId;
    /**
     * 开始节点名称
     */
    private String startName;
    /**
     * 结束节点id
     */
    private String endId;
    /**
     * 结束节点名称
     */
    private String endName;
    /**
     * 关系类型  生产关系，供给关系，归属关系，......(必传)
     */
    private String relationType;

    /**
     * 原材料成本占比
     */
    private String costProportion;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 收入占比
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
     * 市场价格
     */
    private String productPrice;
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
    /**
     * 上游公司供应量占比
     */
    private String supplyProportion;
    /**
     * 下游公司采购量占比
     */
    private String purchaseProportion;
}
