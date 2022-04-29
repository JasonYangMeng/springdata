package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.condition.ProductTreeEntryCondition;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.ProductTreeEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Created by YangMeng on 2021/3/24 10:40
 * 产品族谱
 */
@RestController
@Slf4j
@RequestMapping("productTreeEntry")
public class ProductTreeEntryController {

    @Autowired
    private ProductTreeEntryService productTreeEntryService;

    /**
     * 获取产品族谱词条信息
     *
     * @return
     */
    @GetMapping(value = "getProductTreeEntryList")
    public WebResInfo getProductTreeEntryList(String productEntryId) {
        log.info("getProductTreeEntryList->{}", productEntryId);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            ProductTreeEntryCondition productTreeEntryList = productTreeEntryService.getProductTreeEntryList(productEntryId);
            webResInfo.setData(productTreeEntryList);
        } catch (Exception e) {
            log.error("getProductTreeEntryList error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 修改产品族谱
     *
     * @return
     */
    @PostMapping(value = "updateProductTreeGraph")
    public WebResInfo updateProductTreeGraph(@RequestBody ProductTreeEntryCondition productTreeEntryCondition) {
        log.info("updateProductTreeGraph->{}", productTreeEntryCondition);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            boolean b = productTreeEntryService.updateProductTreeEntry(productTreeEntryCondition);
            webResInfo.setData(b);
        } catch (Exception e) {
            log.error("updateProductTreeGraph error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
