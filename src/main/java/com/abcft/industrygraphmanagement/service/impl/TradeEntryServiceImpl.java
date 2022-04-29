package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.neo.TradeEntryRepository;
import com.abcft.industrygraphmanagement.model.dto.TradeCompanyDto;
import com.abcft.industrygraphmanagement.model.dto.TradeEntryParamDto;
import com.abcft.industrygraphmanagement.model.dto.TradeSubcategoryDto;
import com.abcft.industrygraphmanagement.model.entity.SysIndustryEntity;
import com.abcft.industrygraphmanagement.model.node.TradeEntryNode;
import com.abcft.industrygraphmanagement.service.SysIndustryService;
import com.abcft.industrygraphmanagement.service.TradeEntryService;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author WangZhiZhou
 * @date 2021/5/8
 */
@Service
@Slf4j
public class TradeEntryServiceImpl implements TradeEntryService {

    @Autowired
    private TradeEntryRepository tradeEntryRepository;

    @Override
    public TradeEntryParamDto getCompanyLineChartList(String tradeEntryId) {
        TradeEntryParamDto tradeEntry = new TradeEntryParamDto();
        Optional<TradeEntryNode> byId = tradeEntryRepository.findById(tradeEntryId);
        if (!byId.isPresent()) {
            return tradeEntry;
        }
        TradeEntryNode tradeNode = byId.get();
        tradeEntry.setTradeEntryNode(tradeNode);
        tradeEntry.setTradeCompanyDtoList(getTradeCompanyList(tradeEntryId));
        if (!"3".equals(tradeNode.getLevel())) {
            tradeEntry.setSubcategoryDtoList(getTradeSubcategoryList(tradeEntryId));
        }

        return tradeEntry;
    }

    /**
     * 根据行业词条id获取公司
     *
     * @param tradeEntryId 行业词条id
     * @return List<TradeCompanyDto>
     */
    private List<TradeCompanyDto> getTradeCompanyList(String tradeEntryId) {

        return tradeEntryRepository.getTradeCompanyList(tradeEntryId);
    }

    /**
     * 根据行业词条id获取子行业
     *
     * @param tradeEntryId
     * @return
     */
    private List<TradeSubcategoryDto> getTradeSubcategoryList(String tradeEntryId) {

        return tradeEntryRepository.getTradeSubcategoryList(tradeEntryId);
    }
}
