package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * @Author Created by YangMeng on 2021/4/23 10:43
 */
@Data
@QueryResult
public class EdgesDto {

    /**
     * 开始id
     */
    private String source;
    /**
     * 结束id
     */
    private String target;
}
