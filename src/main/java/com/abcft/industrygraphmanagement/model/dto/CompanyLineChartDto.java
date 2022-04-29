package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * @author WangZhiZhou
 * @date 2021/5/6
 */
@Data
@QueryResult
public class CompanyLineChartDto {

    /**
     * 值
     */
    private String value;

    /**
     * 日期
     */
    private String endDate;

}
