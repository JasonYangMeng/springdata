package com.abcft.industrygraphmanagement.model.entity;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

/**
 * 产品族谱实体类
 *
 * @author WangZhiZhou
 * @date 2021/3/15
 */
@QueryResult
@Data
public class ProductGenealogyEntity {

    /**
     * 产品词条id
     */
    private String productEntryId;
    /**
     * 产品词条名称
     */
    private String productEntryName;

    /**
     * 产品族谱对象列表
     */
    private List<ProductGenealogyEntity> list;
}