package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.dto.TradeEntryParamDto;

/**
 * @author WangZhiZhou
 * @date 2021/5/8
 */
public interface TradeEntryService {

    TradeEntryParamDto getCompanyLineChartList(String tradeEntryId);
}
