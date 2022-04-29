package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.condition.CompanyEntryCondition;
import com.abcft.industrygraphmanagement.model.dto.CompanyEntryDto;
import com.abcft.industrygraphmanagement.model.dto.CompanyLineChartQuarterAndYearDto;
import com.abcft.industrygraphmanagement.model.dto.GraphDto;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.CompanyEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Created by YangMeng on 2021/3/3 15:27
 */
@RestController
@RequestMapping(value = "companyEntry")
@Slf4j
public class CompanyEntryController {

    @Autowired
    private CompanyEntryService companyEntryService;

    /**
     * 获取公司词条
     *
     * @return
     */
    @GetMapping(value = "getCompanyEntryById")
    public WebResInfo getCompanyEntryById(String companyEntryId) {
        log.info("getCompanyEntryById->{}", companyEntryId);
        WebResInfo<CompanyEntryDto> webResInfo = new WebResInfo<>();
        try {
            webResInfo.setCode(WebResCode.Successful);
            CompanyEntryDto companyEntry = companyEntryService.getCompanyEntryById(companyEntryId);
            webResInfo.setData(companyEntry);
        } catch (Exception e) {
            log.error("方法名：getCompanyEntryById, 出现异常:", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }


    /**
     * 获取公司经营、财务、资产负债、现金流数据的季度和年度数据
     *
     * @param companyEntryId 公司id
     * @param label          neo4j节点label
     * @param propertyName   节点属性名
     * @return List<CompanyLineChartDto>
     */
    @GetMapping(value = "getCompanyLineChartList")
    public WebResInfo getCompanyLineChartList(String companyEntryId, String label, String propertyName) {
        log.info("方法getCompanyLineChartList的入参 -> companyEntryId:{}, label:{}, propertyName:{}",
                companyEntryId, label, propertyName);
        WebResInfo<CompanyLineChartQuarterAndYearDto> webResInfo = new WebResInfo<>();
        try {
            webResInfo.setCode(WebResCode.Successful);
            CompanyLineChartQuarterAndYearDto companyLineChart =
                    companyEntryService.getCompanyLineChartList(companyEntryId, label, propertyName);
            webResInfo.setData(companyLineChart);
            log.info("方法名：getCompanyLineChartList, 返回结果：{}", companyLineChart);
        } catch (Exception e) {
            log.error("方法getCompanyLineChartList 出现异常:", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 获取公司产品族谱
     *
     * @return
     */
    @GetMapping(value = "getGraph")
    public WebResInfo getGraph(String companyEntryId, String productEntryId) {
        log.info("getGraph->{},{}", companyEntryId, productEntryId);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            GraphDto graph = companyEntryService.getGraph(companyEntryId, productEntryId);
            webResInfo.setData(graph);
        } catch (Exception e) {
            log.error("getGraph error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 修改公司词条
     *
     * @param companyEntryCondition
     * @return
     */
    @PostMapping(value = "updateCompanyEntry")
    public WebResInfo updateCompanyEntry(@RequestBody CompanyEntryCondition companyEntryCondition) {
        log.info("updateCompanyEntry->companyEntryCondition:{}", companyEntryCondition);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            String s = companyEntryService.updateCompanyEntry(companyEntryCondition);
            webResInfo.setData(s);
        } catch (Exception e) {
            log.error("updateCompanyEntry error:", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 删除数据
     *
     * @param uuid
     * @return
     */
    @GetMapping(value = "deleteById")
    public WebResInfo deleteById(String uuid) {
        log.info("deleteById->uuid:{}", uuid);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            companyEntryService.deleteById(uuid);
        } catch (Exception e) {
            log.error("deleteById error:", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

}