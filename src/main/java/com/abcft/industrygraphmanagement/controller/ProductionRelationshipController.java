package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.dto.DicDto;
import com.abcft.industrygraphmanagement.model.node.ProductionRelationship;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.ProductionRelationshipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/4 15:40
 */
@RestController
@RequestMapping(value = "productionRelationship")
@Slf4j
public class ProductionRelationshipController {

    @Autowired
    private ProductionRelationshipService productionRelationshipService;

    /**
     * 关联公司产品 关系
     *
     * @param startId
     * @param endId
     * @return
     */
    @GetMapping(value = "addRelationship")
    public WebResInfo addRelationship(String startId, String endId) {
        log.info("addRelationship->startId:{}，endId:{}", startId, endId);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            ProductionRelationship productionRelationship = productionRelationshipService.addProductionRelationship(startId, endId);
            webResInfo.setData(productionRelationship);

        } catch (Exception e) {
            log.error("addRelationship error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 根据产品获取供应商信息
     *
     * @param productEntryId
     * @return
     */
    @GetMapping(value = "getCompanyByProductId")
    public WebResInfo getCompanyByProductId(String productEntryId) {
        log.info("getCompanyByProductId->productEntryId:{}", productEntryId);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            List<DicDto> companyByProductId = productionRelationshipService.getCompanyByProductId(productEntryId);
            webResInfo.setData(companyByProductId);
        } catch (Exception e) {
            log.error("getCompanyByProductId error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
