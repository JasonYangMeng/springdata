package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;

/**
 * 查询关系词条详情入参dto
 *
 * @author WangZhiZhou
 * @date 2021/3/30
 */
@Data
public class QueryRelationDetailsParamDto {
    /**
     * 开始节点id
     */
    private String startId;
    /**
     * 开始节点名称
     */
    private String startName;
    /**
     * 供应产品id
     */
    private String supplyProductId;
    /**
     * 供应产品名称
     */
    private String supplyProductName;
    /**
     * 结束节点id
     */
    private String endId;
    /**
     * 结束节点名称
     */
    private String endName;
    /**
     * 终端产品id
     */
    private String terminalProductId;
    /**
     * 终端产品名称
     */
    private String terminalProductName;
    /**
     * 关系类型  生产关系，供给关系，归属关系，......(必传)
     */
    private String relationType;
}
