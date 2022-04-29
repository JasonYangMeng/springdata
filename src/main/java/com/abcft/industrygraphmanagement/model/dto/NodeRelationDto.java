package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 节点关系 Dto
 *
 * @author WangZhiZhou
 * @date 2021/3/19
 */
@Data
@QueryResult
public class NodeRelationDto {
    /**
     * 检索到的词条对象id(必传)
     */
    private String searchedId;
    /**
     * 检索到的词条对象name(必传)
     */
    private String searchedName;
    /**
     * 关系类型  生产关系，供给关系，归属关系，......(必传)
     */
    private String relationType;

    /**
     * 产品对应产业链信息id和名称
     */
    private String[] industries;

    // 下面是所有关系的属性
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
