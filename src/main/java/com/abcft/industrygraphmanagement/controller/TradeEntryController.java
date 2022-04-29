package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.dto.CompanyLineChartQuarterAndYearDto;
import com.abcft.industrygraphmanagement.model.dto.TradeEntryParamDto;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.TradeEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 行业词条
 *
 * @author WangZhiZhou
 * @date 2021/4/2
 */
@RestController
@RequestMapping("tradeEntry")
@Slf4j
public class TradeEntryController {

    @Autowired
    private TradeEntryService tradeEntryService;

    @GetMapping(value = "getTradeEntryDetailById")
    public WebResInfo getTradeEntryDetailById(String tradeEntryId) {
        log.info("方法getTradeEntryDetailById的入参 -> tradeEntryId:{}", tradeEntryId);
        WebResInfo<TradeEntryParamDto> webResInfo = new WebResInfo<>();
        try {
            webResInfo.setCode(WebResCode.Successful);
            TradeEntryParamDto tradeEntry = tradeEntryService.getCompanyLineChartList(tradeEntryId);
            webResInfo.setData(tradeEntry);
            log.info("方法名：getTradeEntryDetailById, 返回结果：{}", tradeEntry);
        } catch (Exception e) {
            log.error("方法getTradeEntryDetailById 出现异常:", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
