package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 工商数据 高管人员信息
 *
 * @author WangZhiZhou
 * @date 2021/4/29
 */
@Data
@QueryResult
public class SeniorManagerDto {
    /**
     * 高管人员id
     */
    private String id;

    /**
     * 高管人员名字
     */
    private String name;

    /**
     * 职位
     */
    private String post;

    /**
     * 期末持股数(股)
     */
    private String shareHoldNum;

    /**
     * 期末参考持股市值（元）
     */
    private String shareHoldRefVal;
}
