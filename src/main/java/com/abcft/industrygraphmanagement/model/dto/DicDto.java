package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * @Author Created by YangMeng on 2021/3/15 16:05
 */
@Data
@QueryResult
public class DicDto {
    private String companyEntryId;
    private String companyName;
}
