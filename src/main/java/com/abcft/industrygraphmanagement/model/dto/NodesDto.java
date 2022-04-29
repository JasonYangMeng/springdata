package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/4/23 10:44
 */
@Data
@QueryResult
public class NodesDto {

    private String id;
    /**
     * 名称
     */
    private String label;
    /**
     * 公司
     */
    private List<DicDto> companyList;
}
