package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 行业子分类Dto
 *
 * @author WangZhiZhou
 * @date 2021/4/6
 */
@Data
@QueryResult
public class TradeSubcategoryDto {
    /**
     * 行业子分类id
     */
    private String tradeSubcategoryId;

    /**
     * 行业分类名称
     */
    private String name;

    /**
     * 行业描述，简介
     */
    private String introduction;

    /**
     * 公司数量
     */
    private String companyQuantity;
}
