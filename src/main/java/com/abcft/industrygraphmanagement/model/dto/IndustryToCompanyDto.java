package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/4/23 10:42
 */
@Data
@QueryResult
public class IndustryToCompanyDto {
    /**
     * 线集合
     */
    private List<EdgesDto> edges;
    /**
     * 节点集合
     */
    private List<NodesDto> nodes;
    /**
     * 产业链名称
     */
    private String industryName;
    /**
     * 产业链id
     */
    private String industryId;
}
