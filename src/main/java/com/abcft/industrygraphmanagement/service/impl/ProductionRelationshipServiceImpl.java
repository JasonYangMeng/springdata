package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.neo.CompanyEntryRepository;
import com.abcft.industrygraphmanagement.dao.neo.ProductEntryRepository;
import com.abcft.industrygraphmanagement.dao.neo.ProductionRelationshipRepository;
import com.abcft.industrygraphmanagement.model.dto.DicDto;
import com.abcft.industrygraphmanagement.model.node.CompanyEntryNode;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import com.abcft.industrygraphmanagement.model.node.ProductionRelationship;
import com.abcft.industrygraphmanagement.service.ProductionRelationshipService;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author Created by YangMeng on 2021/3/4 15:32
 */
@Service
public class ProductionRelationshipServiceImpl implements ProductionRelationshipService {

    @Autowired
    private ProductionRelationshipRepository productionRelationshipRepository;

    @Autowired
    private CompanyEntryRepository companyEntryRepository;

    @Autowired
    private ProductEntryRepository productEntryRepository;

    /**
     * 添加公司产品 关系
     *
     * @param startNode
     * @param toNode
     * @return
     */
    @Override
    public ProductionRelationship addProductionRelationship(CompanyEntryNode startNode, ProductEntryNode toNode) {
        ProductionRelationship productionRelationship = new ProductionRelationship();
        productionRelationship.setStartNode(startNode);
        productionRelationship.setEndNode(toNode);
        //添加属性
        productionRelationship.setUuid(UuidUtils.generate());
        ProductionRelationship save = productionRelationshipRepository.save(productionRelationship);
        return save;
    }

    /**
     * 添加公司产品 关系
     *
     * @param startNodeId
     * @param toNodeId
     * @return
     */
    @Override
    public ProductionRelationship addProductionRelationship(String startNodeId, String toNodeId) {
        Optional<CompanyEntryNode> byId = companyEntryRepository.findById(startNodeId);
        Optional<ProductEntryNode> byId1 = productEntryRepository.findById(toNodeId);
        if (byId.isPresent() && byId1.isPresent()) {
            return addProductionRelationship(byId.get(), byId1.get());
        }
        return new ProductionRelationship();
    }

    /**
     * 获取产品的供应商公司
     *
     * @param productEntryId
     * @return
     */
    @Override
    public List<DicDto> getCompanyByProductId(String productEntryId) {
        return productionRelationshipRepository.getCompanyByProductId(productEntryId);
    }
}
