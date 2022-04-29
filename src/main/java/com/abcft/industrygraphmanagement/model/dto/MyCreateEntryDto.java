package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * @author WangZhiZhou
 * @date 2021/5/18
 */
@Data
@QueryResult
public class MyCreateEntryDto {
    /**
     * 词条名称
     */
    private String name;

    /**
     * 词条类型
     */
    private String label;

    /**
     * 词条审核状态
     */
    private String auditStatus;

    /**
     * 词条审核意见
     */
    private String auditMind;

    /**
     * 词条创建时间
     */
    private String createTime;

    /**
     * 词条最近修改时间
     */
    private String modifyTime;
}
