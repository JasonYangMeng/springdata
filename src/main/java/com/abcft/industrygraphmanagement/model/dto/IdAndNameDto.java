package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * @Author Created by YangMeng on 2021/6/21 14:24
 */
@Data
@QueryResult
public class IdAndNameDto {
    private String id;
    private String name;
}
