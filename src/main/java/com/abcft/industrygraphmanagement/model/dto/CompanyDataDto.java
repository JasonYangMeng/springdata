package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

/**
 * 公司行情数据、经营数据、资产负债数据、财务数据、现金流量表数据dto
 *
 * @author WangZhiZhou
 * @date 2021/5/6
 */
@Data
@QueryResult
public class CompanyDataDto {

    /**
     * 指标名称
     */
    private String name;

    /**
     * 属性名
     */
    private String propertyName;

    /**
     * 属性值
     */
    private String value;

    /**
     * 属性值1
     */
    private String value1;

}
