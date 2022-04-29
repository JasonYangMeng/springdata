package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * @Author Created by YangMeng on 2021/3/11 16:13
 */
@Data
@QueryResult
public class  ProductDto {

    /**
     * 关系uuid
     */
    private String uuid;

    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品uuid
     */
    private String productEntryId;

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 收入占比
     */
    private String incomeProportion;

    /**
     * 毛利率
     */
    private String productGross;

    /**
     * 产品单价
     */
    private String productPrice;

    /**
     * 产能
     */
    private String capacity;
    /**
     * 市场份额占比
     */
    private String marketShares;

    /**
     * 产能利用率
     */
    private String capacityRatio;

    /**
     * 产能占比
     */
    private String capacityProportion;
}
