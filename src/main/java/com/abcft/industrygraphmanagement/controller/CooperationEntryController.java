package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.condition.CooperationCondition;
import com.abcft.industrygraphmanagement.model.dto.GraphDto;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.CooperationEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Created by YangMeng on 2021/3/24 10:41
 * 生产协作图
 */
@RestController
@RequestMapping(value = "cooperation")
@Slf4j
public class CooperationEntryController {

    @Autowired
    private CooperationEntryService cooperationEntryService;

    /**
     * 获取生产协作图信息
     *
     * @return
     */
    @GetMapping(value = "getCooperationEntryList")
    public WebResInfo getCooperationEntryList(String companyEntryId, String productEntryId) {
        log.info("getCooperationEntryList->{},{}", companyEntryId, productEntryId);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            CooperationCondition cooperationEntryList = cooperationEntryService.getCooperationEntryList(companyEntryId, productEntryId);
            webResInfo.setData(cooperationEntryList);
        } catch (Exception e) {
            log.error("getCooperationEntryList error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 获取生产协作图图谱
     *
     * @return
     */
    @GetMapping(value = "getCooperationGraph")
    public WebResInfo getCooperationGraph(String companyEntryId, String productEntryId) {
        log.info("getCooperationGraph->{},{}", companyEntryId, productEntryId);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            GraphDto cooperationGraph = cooperationEntryService.getCooperationGraph(companyEntryId, productEntryId);
            webResInfo.setData(cooperationGraph);
        } catch (Exception e) {
            log.error("getCooperationGraph error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 修改生产协作图
     *
     * @return
     */
    @PostMapping(value = "updateCooperation")
    public WebResInfo updateCooperation(@RequestBody CooperationCondition cooperationCondition) {
        log.info("updateCooperation->{}", cooperationCondition);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            boolean b = cooperationEntryService.updateCooperation(cooperationCondition);
            webResInfo.setData(b);
        } catch (Exception e) {
            webResInfo.setData(false);
            log.error("updateCooperation error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
