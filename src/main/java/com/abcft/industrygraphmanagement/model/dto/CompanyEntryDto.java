package com.abcft.industrygraphmanagement.model.dto;

import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;
import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;
import com.abcft.industrygraphmanagement.model.node.*;
import lombok.Data;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/11 14:14
 */
@Data
public class CompanyEntryDto extends EntryInfoSettingDto {

    /**
     * 公司词条
     */
    private CompanyEntryNode companyEntryNode;

    /**
     * 词条信息来源
     */
    private List<EntryInfoSourceEntity> entryInfoSourceEntityList;

    /**
     * 词条链接
     */
    private List<EntryLinkEntity> entryLinkEntityList;

    /**
     * 产品 列表
     */
    private List<ProductDto> productDtoList;

    /**
     * 公司-公司 每个产品供应商
     */
    private List<ProductToCompanyDto> productToParentCompanyDtoList;

    /**
     * 公司-公司 产品对应的下游公司 的产品
     */
    private List<ProductToCompanyDto> productToChildCompanyDtoList;

    /**
     * 主营产品相关产业链
     */
    //TODO

    /**
     * 工商数据股东信息
     */
    private List<ShareholderInformationDto> shareholderInfoList;

    /**
     * 工商数据高管信息
     */
    private List<SeniorManagerDto> seniorManagerList;

    /**
     * 公司行情数据
     */
    private MarketDataNode marketDataNode;

    /**
     * 公司经营数据
     */
    private List<CompanyDataDto> operateDataList;

    /**
     * 公司财务数据
     */
    private List<CompanyDataDto> financeDataList;

    /**
     * 公司资产负债数据
     */
    private List<CompanyDataDto> assetsLiabilitiesList;

    /**
     * 公司现金流量数据
     */
    private List<CompanyDataDto> cashFlowDataList;
}
