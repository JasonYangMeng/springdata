package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.mysql.ProductMapper;
import com.abcft.industrygraphmanagement.model.entity.ProductEntity;
import com.abcft.industrygraphmanagement.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/9
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    /**
     * 0：未创建
     */
    private static final String NOT_CREATE = "0";

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductEntity> queryProductNameListByTypeAndName(String type, String productName) {
        return productMapper.queryProductNameListByTypeAndName(type, productName);
    }

    @Override
    public ProductEntity queryProductNameAccurateList(String name) {
        return productMapper.queryProductNameAccurateList(name);
    }

    @Override
    public List<ProductEntity> queryProductNameListByName(String productName) {
        return productMapper.queryProductNameListByName(productName);
    }

    /**
     * 更改产品的区分类型和是否创建词条状态
     *
     * @param productEntryId
     * @param createdEntry
     * @param type
     */
    @Override
    public void updateProductById(String productEntryId, String createdEntry, String type) {
        productMapper.updateProductById(productEntryId, createdEntry, type);
    }

    @Override
    public void updateIndustryStatusById(String productEntryId) {
        productMapper.updateIndustryStatusById(productEntryId);
    }

    @Override
    public void updateProductTreeStatusById(String productEntryId) {
        productMapper.updateProductTreeStatusById(productEntryId);
    }

    @Override
    public void updateArtworkStatusById(String productEntryId) {
        productMapper.updateArtworkStatusById(productEntryId);
    }

    /**
     * 防止在mysql中创建重复产品名字
     *
     * @param productEntity
     */
    @Override
    public void addProduct(ProductEntity productEntity) throws Exception {
        String productName = productEntity.getProductName();
        if (StringUtils.isEmpty(productName)) {
            throw new Exception("产品名称不能为空！");
        }
        ProductEntity productEntity1 = productMapper.queryProductByName(productName);
        if (productEntity1 != null) {
            throw new Exception("为避免重复创建，请选中下拉框中的" + productName);
        }
        productMapper.addProduct(productEntity);
    }

    @Override
    public void updateProductName(String productId, String productName) {
        productMapper.updateProductName(productId, productName);
    }

    @Override
    public ProductEntity queryProductByName(String name) {
        return productMapper.queryProductByName(name);
    }

}
