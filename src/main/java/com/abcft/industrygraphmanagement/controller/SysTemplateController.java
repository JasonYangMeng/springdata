package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.entity.SysTemplateEntity;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.SysTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Created by YangMeng on 2021/3/9 16:14
 */
@RestController
@RequestMapping(value = "sysTemplate")
@Slf4j
public class SysTemplateController {
    @Autowired
    private SysTemplateService sysTemplateService;

    /**
     * 获取模板
     *
     * @param id
     * @return 模板
     */
    @GetMapping(value = "getTemplateById")
    public WebResInfo getTemplateById(@RequestParam String id) {
        log.info("getTemplateById -> condition:{}", id);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            SysTemplateEntity templateById = sysTemplateService.getTemplateById(id);
            webResInfo.setData(templateById);
        } catch (Exception e) {
            log.error("getTemplateById error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
