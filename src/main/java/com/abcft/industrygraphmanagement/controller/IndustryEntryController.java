package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.condition.IndustryEntryCondition;
import com.abcft.industrygraphmanagement.model.dto.IdAndNameDto;
import com.abcft.industrygraphmanagement.model.dto.IndustryToCompanyDto;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.IndustryEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/22 18:01
 */
@RestController
@Slf4j
@RequestMapping("industryEntry")
public class IndustryEntryController {

    @Autowired
    private IndustryEntryService industryEntryService;

    /**
     * 获取产业链词条信息
     *
     * @return
     */
    @GetMapping(value = "getIndustryEntryList")
    public WebResInfo getIndustryEntryList(String industryEntryId) {
        log.info("getIndustryEntryList->{}", industryEntryId);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            IndustryEntryCondition industryEntryList = industryEntryService.getIndustryEntryList(industryEntryId);
            webResInfo.setData(industryEntryList);
        } catch (Exception e) {
            log.error("getIndustryEntryList error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 获取产业链id和名称
     *
     * @return
     */
    @GetMapping(value = "getIdAndNameList")
    public WebResInfo getIdAndNameList(String name) {
        log.info("getIdAndNameList->{}", name);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            List<IdAndNameDto> idAndName = industryEntryService.getIdAndName(name);
            webResInfo.setData(idAndName);
        } catch (Exception e) {
            log.error("getIdAndNameList error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 获取产业链图谱
     *
     * @return
     */
    @GetMapping(value = "getIndustryGraph")
    public WebResInfo getIndustryGraph(String productEntryId, @RequestParam(name = "self", required = false, defaultValue = "true") boolean self, @RequestParam(name = "level", defaultValue = "6", required = false) Integer level) {
        log.info("getIndustryGraph->{}", productEntryId);
        WebResInfo webResInfo = new WebResInfo();
        try {
            if (level == null || level < 1 || level > 10) {
                level = 6;
            }
            webResInfo.setCode(WebResCode.Successful);
            List<IndustryToCompanyDto> industryGraphList = industryEntryService.getIndustryGraphList(productEntryId, self, level);
            webResInfo.setData(industryGraphList);
        } catch (Exception e) {
            log.error("getIndustryGraph error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 修改产业链图谱
     *
     * @return
     */
    @PostMapping(value = "updateIndustryGraph")
    public WebResInfo updateIndustryGraph(@RequestBody IndustryEntryCondition industryEntryCondition) {
        log.info("updateIndustryGraph->{}", industryEntryCondition);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            boolean b = industryEntryService.updateIndustryEntry(industryEntryCondition);
            webResInfo.setData(b);
        } catch (Exception e) {
            log.error("updateIndustryGraph error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
