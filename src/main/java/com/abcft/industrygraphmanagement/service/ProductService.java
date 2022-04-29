package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.entity.ProductEntity;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/9
 */
public interface ProductService {

    List<ProductEntity> queryProductNameListByTypeAndName(String type, String productName);

    ProductEntity queryProductNameAccurateList(String name);

    List<ProductEntity> queryProductNameListByName(String productName);

    void updateProductById(String productEntryId, String createdEntry, String type);

    void updateIndustryStatusById(String productEntryId);

    void updateProductTreeStatusById(String productEntryId);

    void updateArtworkStatusById(String productEntryId);

    void addProduct(ProductEntity productEntity) throws Exception;

    void updateProductName(String productId, String productName);

    ProductEntity queryProductByName(String name);
}
