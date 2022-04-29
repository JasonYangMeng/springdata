package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.condition.IndustryEntryCondition;
import com.abcft.industrygraphmanagement.model.dto.IdAndNameDto;
import com.abcft.industrygraphmanagement.model.dto.IndustryToCompanyDto;
import com.abcft.industrygraphmanagement.model.dto.ProductToProductDto;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/20 14:08
 */
public interface IndustryEntryService {

    /**
     * 添加产业链词条
     *
     * @param entryIndustryCondition
     * @return
     */
    boolean updateIndustryEntry(IndustryEntryCondition entryIndustryCondition) throws Exception;

    /**
     * 获取产业链图谱数据
     *
     * @param productEntryId
     * @return
     */
    List<IndustryToCompanyDto> getIndustryGraphList(String productEntryId, boolean self, int level) throws Exception;

    /**
     * 查询产业链id和名称
     *
     * @param name
     * @return
     */
    List<IdAndNameDto> getIdAndName(String name);

    /**
     * 获取产业链词条信息
     *
     * @param industryEntryId
     * @return
     */
    IndustryEntryCondition getIndustryEntryList(String industryEntryId);

    /**
     * 添加产品之间的关系
     *
     * @param productToProductDtoList
     */
    void createProductRelationship(List<ProductToProductDto> productToProductDtoList);

    /**
     * 获取产品对应子集列表
     *
     * @param productEntryIds
     * @param productToProductDtoList
     */
    void getProductToProductList(List<String> productEntryIds, List<ProductToProductDto> productToProductDtoList);

    /**
     * 设置图谱
     *
     * @param productEntryIds
     * @param industryToCompanyDtoList
     * @param containsCompany
     */
    void setIndustryGraph(List<String> productEntryIds, List<IndustryToCompanyDto> industryToCompanyDtoList, boolean containsCompany, int level);
}
