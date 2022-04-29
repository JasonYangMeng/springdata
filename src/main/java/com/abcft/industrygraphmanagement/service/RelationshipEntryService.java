package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.dto.QueryRelationDetailsParamDto;
import com.abcft.industrygraphmanagement.model.dto.RelationshipEntryDto;
import com.abcft.industrygraphmanagement.model.dto.RelationshipEntryParamDto;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/24
 */
public interface RelationshipEntryService {

    /**
     * 关系词条创建关系接口
     *
     * @param relationshipEntryParamDto RelationshipEntryParamDto
     */
    void createRelationship(RelationshipEntryParamDto relationshipEntryParamDto) throws Exception;

    /**
     * 查询关系详情接口
     *
     * @param queryRelationDetailsDto 查询关系详情入参dto
     * @return 关系详情
     */
    RelationshipEntryDto queryRelationDetails(QueryRelationDetailsParamDto queryRelationDetailsDto) throws Exception;

    /**
     * 通过公司词条id 查询 该公司下的所有产品
     *
     * @param companyEntryId 公司词条id
     * @return List<ProductEntryNode>
     */
    List<ProductEntryNode> queryProductsByCompanyEntryId(String companyEntryId);
}
