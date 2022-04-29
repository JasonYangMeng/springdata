package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * @Author Created by YangMeng on 2021/3/12 10:30
 */
@Data
@QueryResult
public class SupplyParentDto {

    /**
     * 关系id
     */
    private String uuid;

    /**
     * 供应商id
     */
    private String companyEntryId;

    /**
     * 供给产品词条id
     */
    private String productEntryId;

    /**
     * 供应商产品名称
     */
    private String productName;
    /**
     * 当前产品id
     */
    private String currentProductId;
    /**
     * 当前产品名称
     */
    private String currentProductName;

    /**
     * 供应商名称
     */
    private String companyName;
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

}
