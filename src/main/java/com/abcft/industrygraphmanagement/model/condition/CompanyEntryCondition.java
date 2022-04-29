package com.abcft.industrygraphmanagement.model.condition;

import com.abcft.industrygraphmanagement.model.dto.ProductDto;
import com.abcft.industrygraphmanagement.model.dto.SupplyParentDto;
import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;
import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;
import com.abcft.industrygraphmanagement.model.node.*;
import lombok.Data;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/9 11:29
 */
@Data
public class CompanyEntryCondition {
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
     * 公司-产品 生产关系
     */
    private List<ProductDto> productDtoList;

    /**
     * 公司-公司 供应商 n-1
     */
    private List<SupplyParentDto> supplyRelationshipParentList;

    /**
     * 公司-公司 下游 1-n
     */
    private List<SupplyParentDto> supplyRelationshipChildList;

    /**
     * 经营数据
     */
    private OperateDataNode operateDataNode;

    /**
     * 利润表
     */
    private FinanceDataNode financeDataNode;

    /**
     * 现金流量表
     */
    private CashFlowDataNode cashFlowDataNode;

    /**
     * 资产负债表
     */
    private AssetsLiabilitiesNode assetsLiabilitiesNode;


    /**
     * 经营数据
     */
    private OperateDataNode operateDataNode1;

    /**
     * 利润表
     */
    private FinanceDataNode financeDataNode1;

    /**
     * 现金流量表
     */
    private CashFlowDataNode cashFlowDataNode1;

    /**
     * 资产负债表
     */
    private AssetsLiabilitiesNode assetsLiabilitiesNode1;

}
