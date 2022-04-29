package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.neo.AscriptionRelationshipRepository;
import com.abcft.industrygraphmanagement.dao.neo.ProductEntryRepository;
import com.abcft.industrygraphmanagement.dao.neo.ProductTreeEntryRepository;
import com.abcft.industrygraphmanagement.dao.neo.ProductTreeRelationshipRepository;
import com.abcft.industrygraphmanagement.model.condition.ProductTreeEntryCondition;
import com.abcft.industrygraphmanagement.model.dto.ProductToProductDto;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import com.abcft.industrygraphmanagement.model.node.ProductTreeEntryNode;
import com.abcft.industrygraphmanagement.model.node.ProductTreeRelationship;
import com.abcft.industrygraphmanagement.service.IndustryEntryService;
import com.abcft.industrygraphmanagement.service.ProductService;
import com.abcft.industrygraphmanagement.service.ProductTreeEntryService;
import com.abcft.industrygraphmanagement.util.DateExtUtils;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author Created by YangMeng on 2021/3/23 17:57
 */
@Service
@Slf4j
public class ProductTreeEntryServiceImpl implements ProductTreeEntryService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductEntryRepository productEntryRepository;

    @Autowired
    private ProductTreeEntryRepository productTreeEntryRepository;

    @Autowired
    private ProductTreeRelationshipRepository productTreeRelationshipRepository;

    @Autowired
    private IndustryEntryService industryEntryService;

    @Autowired
    private AscriptionRelationshipRepository ascriptionRelationshipRepository;

    /**
     * 获取产品族谱词条信息
     *
     * @param productTreeEntryId
     * @return
     */
    @Override
    public ProductTreeEntryCondition getProductTreeEntryList(String productTreeEntryId) {
        ProductTreeEntryCondition productTreeEntryCondition = new ProductTreeEntryCondition();
        Optional<ProductTreeEntryNode> byId = productTreeEntryRepository.findById(productTreeEntryId);
        if (byId.isPresent()) {
            ProductTreeEntryNode entryNode = byId.get();
            productTreeEntryCondition.setProductTreeEntryNode(entryNode);
        }
        //获取
        List<ProductToProductDto> productToProductDtoList = new ArrayList<>();
        getProductToProductList(Arrays.asList(productTreeEntryId), productToProductDtoList);
        productTreeEntryCondition.setProductToProductDtoList(productToProductDtoList);
        return productTreeEntryCondition;
    }

    /**
     * 添加产品族谱
     *
     * @param productTreeEntryCondition
     * @return
     */
    @Override
    public boolean updateProductTreeEntry(ProductTreeEntryCondition productTreeEntryCondition) {
        ProductTreeEntryNode productTreeEntryNode = productTreeEntryCondition.getProductTreeEntryNode();
        String productTreeId = productTreeEntryNode.getProductTreeId();
        if (StringUtils.isEmpty(productTreeId)) {
            return false;
        }
        //修改基本信息
        String dateStr = DateExtUtils.getCurrentDateStr();
        productTreeEntryNode.setModifyTime(dateStr);
        if (StringUtils.isEmpty(productTreeEntryNode.getCreateTime())) {
            productTreeEntryNode.setCreateTime(dateStr);
        }
        //TODO 版本叠加
        productTreeEntryNode.setVersion("0.2");
        ProductTreeEntryNode productTreeEntryNode1 = productTreeEntryRepository.save(productTreeEntryNode);
        List<ProductToProductDto> productToProductDtoList = productTreeEntryCondition.getProductToProductDtoList();
        //删除工艺图下的关系
        productTreeEntryRepository.deleteProductRelation(productTreeId);
        //创建工艺对应产品的工艺关系
        updateProductEntry(productTreeEntryNode1);
        if (!CollectionUtils.isEmpty(productToProductDtoList)) {
            //添加关系
            industryEntryService.createProductRelationship(productToProductDtoList);
        }
        //修改产品创建状态
        productService.updateProductTreeStatusById(productTreeId);
        return true;
    }

    /**
     * 获取产品 归属的列表
     *
     * @param productEntryIds
     * @param productToProductDtoList
     */
    public void getProductToProductList(List<String> productEntryIds, List<ProductToProductDto> productToProductDtoList) {
        if (CollectionUtils.isEmpty(productEntryIds)) {
            return;
        }
        List<ProductToProductDto> item = ascriptionRelationshipRepository.getProductToProductList(productEntryIds);
        productToProductDtoList.addAll(item);
        List<String> ids = item.stream().map(a -> a.getEndId()).collect(Collectors.toList());
        getProductToProductList(ids, productToProductDtoList);
    }

    /**
     * 创建工艺图对应产品的生产工艺图关系
     */
    private void updateProductEntry(ProductTreeEntryNode productTreeEntryNode) {
        String productTreeId = productTreeEntryNode.getProductTreeId();
        if (productTreeRelationshipRepository.existsRelationship(productTreeId)) {
            return;
        }

        ProductEntryNode save;
        if (!productEntryRepository.existsById(productTreeId)) {
            ProductEntryNode productEntryNode = new ProductEntryNode();
            productEntryNode.setProductEntryId(productTreeId);
            productEntryNode.setName(productTreeEntryNode.getName().replaceAll("产品族谱", ""));
            save = productEntryRepository.save(productEntryNode);
        } else {
            Optional<ProductEntryNode> byId = productEntryRepository.findById(productTreeId);
            save = byId.get();
        }
        ProductTreeRelationship productTreeRelationship = new ProductTreeRelationship();
        productTreeRelationship.setStartNode(productTreeEntryNode);
        productTreeRelationship.setEndNode(save);
        productTreeRelationship.setUuid(UuidUtils.generate());
        productTreeRelationshipRepository.save(productTreeRelationship);
    }
}
