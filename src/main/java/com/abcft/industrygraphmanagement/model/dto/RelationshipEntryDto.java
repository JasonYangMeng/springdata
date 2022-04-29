package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 关系词条dto
 *
 * @author WangZhiZhou
 * @date 2021/3/24
 */
@Data
@QueryResult
public class RelationshipEntryDto {

    private String uuid;

    /**
     * 创建关系词条的关系词条名称，例如：阿博茨公司 生产 产业链图谱
     */
    private String relationEntryName;

    /**
     * 开始节点id
     */
    private String startId;
    /**
     * 开始节点名称
     */
    private String startName;
    /**
     * 供应产品id
     */
    private String supplyProductId;
    /**
     * 供应产品名称
     */
    private String supplyProductName;
    /**
     * 结束节点id
     */
    private String endId;
    /**
     * 结束节点名称
     */
    private String endName;
    /**
     * 终端产品id
     */
    private String terminalProductId;
    /**
     * 终端产品名称
     */
    private String terminalProductName;
    /**
     * 关系类型  生产关系，供给关系，归属关系，......(必传)
     */
    private String relationType;
    /**
     * 上游公司供应量占比
     */
    private String supplyProportion;
    /**
     * 下游公司采购量占比
     */
    private String purchaseProportion;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 占主营收入比 / 收入占比
     */
    private String incomeProportion;
    /**
     * 产品毛利率
     */
    private String productGross;
    /**
     * 产品单价
     */
    private String productPrice;
    /**
     * 原材料成本占比
     */
    private String costProportion;
    /**
     * 任职职位
     */
    private String position;
    /**
     * 任职年限 / 任职期限
     */
    private String positionTerm;
    /**
     * 持股比例 / 股权比例
     */
    private String shareholdingRatio;
}
