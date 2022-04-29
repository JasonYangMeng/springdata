package com.abcft.industrygraphmanagement.model.dto;

import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;
import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;
import com.abcft.industrygraphmanagement.model.node.TradeEntryNode;
import lombok.Data;

import java.util.List;

/**
 * 行业词条入参dto
 *
 * @author WangZhiZhou
 * @date 2021/4/2
 */
@Data
public class TradeEntryParamDto {

    /**
     * 行业词条节点
     */
    private TradeEntryNode tradeEntryNode;

    /**
     * 词条信息来源对象列表
     */
    private List<EntryInfoSourceEntity> entryInfoSourceEntityList;

    /**
     * 词条链接对象列表
     */
    private List<EntryLinkEntity> entryLinkEntityList;

    /**
     * 行业子分类
     */
    private List<TradeSubcategoryDto> subcategoryDtoList;

    /**
     * 行业下的公司
     */
    private List<TradeCompanyDto> tradeCompanyDtoList;
}
