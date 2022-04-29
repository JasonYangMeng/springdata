package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.neo.MaterialsRelationshipRepository;
import com.abcft.industrygraphmanagement.dao.neo.ProductEntryRepository;
import com.abcft.industrygraphmanagement.model.dto.ProductDto;
import com.abcft.industrygraphmanagement.model.node.MaterialsRelationship;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import com.abcft.industrygraphmanagement.service.MaterialsRelationshipService;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/15 14:35
 */
@Service
public class MaterialsRelationshipServiceImpl implements MaterialsRelationshipService {

    @Autowired
    private MaterialsRelationshipRepository materialsRelationshipRepository;

    @Autowired
    private ProductEntryRepository productEntryRepository;

    /**
     * 添加原材料关系
     *
     * @param startName
     * @param endName
     */
    @Override
    public boolean addRelationship(String startName, String endName) {
        if (StringUtils.isEmpty(startName) || StringUtils.isEmpty(endName)) {
            return false;
        }
        ProductEntryNode productEntryNode = productEntryRepository.queryProductEntry(startName);
        ProductEntryNode productEntryNode1 = productEntryRepository.queryProductEntry(endName);
        if (productEntryNode == null || productEntryNode1 == null) {
            return false;
        }
        MaterialsRelationship materialsRelationship = new MaterialsRelationship();
        materialsRelationship.setUuid(UuidUtils.generate());
        materialsRelationship.setStartNode(productEntryNode);
        materialsRelationship.setEndNode(productEntryNode1);
        materialsRelationshipRepository.save(materialsRelationship);
        return true;
    }

    /**
     * 获取父级产品
     *
     * @param productId
     * @return
     */
    @Override
    public List<ProductDto> getParentProductById(String productId) {
        return materialsRelationshipRepository.queryParentProductById(productId);
    }

    /**
     * 获取子级产品
     *
     * @param productId
     * @return
     */
    @Override
    public List<ProductDto> getChildProductById(String productId) {
        return materialsRelationshipRepository.queryChildProductById(productId);
    }
}
