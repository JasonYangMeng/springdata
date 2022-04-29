package com.abcft.industrygraphmanagement.dao.mysql;

import com.abcft.industrygraphmanagement.model.entity.ProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/9
 */
@Repository
@Mapper
public interface ProductMapper {

    ProductEntity queryProductNameAccurateList(String name);

    List<ProductEntity> queryProductNameListByName(String productName);

    List<ProductEntity> queryProductNameListByTypeAndName(String type, String productName);

    void updateProductById(String productEntryId, String createdEntry, String type);

    /**
     * 修改产业链状态
     *
     * @param productEntryId
     */
    void updateIndustryStatusById(String productEntryId);
    /**
     * 修改产品族谱状态
     *
     * @param productEntryId
     */
    void updateProductTreeStatusById(String productEntryId);


    /**
     * 修改产品工艺状态
     *
     * @param productEntryId
     */
    void updateArtworkStatusById(String productEntryId);

    void addProduct(ProductEntity productEntity);

    ProductEntity queryProductByName(String name);

    /**
     * 修改产品状态
     * @param productEntryId
     */
    void updateProductStatus(String  productEntryId);

    void updateProductName(String productId, String productName);
}
