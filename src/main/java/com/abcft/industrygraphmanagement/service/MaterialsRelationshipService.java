package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.dto.ProductDto;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/15 14:32
 * 原材料
 */
public interface MaterialsRelationshipService {

    /**
     * 添加原材料关系
     *
     * @param startName
     * @param endName
     */
    boolean addRelationship(String startName, String endName);

    /**
     * 获取父级产品
     *
     * @param uuid
     * @return
     */
    List<ProductDto> getParentProductById(String uuid);

    /**
     * 获取子级产品
     *
     * @param uuid
     * @return
     */
    List<ProductDto> getChildProductById(String uuid);
}
