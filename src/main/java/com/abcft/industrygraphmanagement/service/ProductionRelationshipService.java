package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.dto.DicDto;
import com.abcft.industrygraphmanagement.model.node.CompanyEntryNode;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import com.abcft.industrygraphmanagement.model.node.ProductionRelationship;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/4 15:29
 */
public interface ProductionRelationshipService {

    /**
     * 添加公司产品 关系
     *
     * @param startNode
     * @param toNode
     * @return
     */
    ProductionRelationship addProductionRelationship(CompanyEntryNode startNode, ProductEntryNode toNode);

    /**
     * 添加公司产品 关系
     *
     * @param startNodeId
     * @param toNodeId
     * @return
     */
    ProductionRelationship addProductionRelationship(String startNodeId, String toNodeId);

    /**
     * 获取产品的供应商公司
     *
     * @param productEntryId
     * @return
     */
    List<DicDto> getCompanyByProductId(String productEntryId);
}
