package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.entity.CompanyEntity;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <>
 * <功能描述>
 *
 * @date 2021/3/9
 */
@RestController
@RequestMapping("/company")
@Slf4j
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    /**
     * 公司词条名称模糊查询,展示前十条
     *
     * @param companyName 查询公司名称参数
     * @return WebResInfo<List < CompanyEntryEntity>> 公司词条对象列表
     */
    @GetMapping("/queryCompanyName")
    public WebResInfo<List<CompanyEntity>> queryCompanyNameList(@RequestParam String companyName) {
        log.info("queryEntryNameList -> condition:{}", companyName);
        List<CompanyEntity> list = new ArrayList<>();
        try {
            list = companyService.queryCompanyNameList(companyName);
        } catch (Exception e) {
            log.error("queryEntryNameList -> exception:{}", e);
        }
        return new WebResInfo(WebResCode.Successful, list, null);
    }
}
