package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.condition.ProductTreeEntryCondition;

/**
 * @Author Created by YangMeng on 2021/3/23 17:57
 */
public interface ProductTreeEntryService {
    /**
     * 获取产品族谱词条信息
     *
     * @param productTreeEntryId
     * @return
     */
    ProductTreeEntryCondition getProductTreeEntryList(String productTreeEntryId);

    /**
     * 添加产品族谱
     *
     * @param productTreeEntryCondition
     * @return
     */
    boolean updateProductTreeEntry(ProductTreeEntryCondition productTreeEntryCondition);
}
