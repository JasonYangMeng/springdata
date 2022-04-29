package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.dto.ProductDto;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.MaterialsRelationshipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/15 15:06
 */
@RestController
@RequestMapping(value = "materialsRelationship")
@Slf4j
public class MaterialsRelationshipController {

    @Autowired
    private MaterialsRelationshipService materialsRelationshipService;


    /**
     * 添加 产品种类关系
     *
     * @param startName
     * @param endName
     * @return
     */
    @GetMapping(value = "addRelationship")
    public WebResInfo addRelationship(String startName, String endName) {
        log.info("addRelationship->addRelationship:{}", startName, endName);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            boolean b = materialsRelationshipService.addRelationship(startName, endName);
            webResInfo.setData(b);
        } catch (Exception e) {
            log.error("addRelationship error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 获取父级产品名称
     *
     * @param productId 产品id
     * @return
     */
    @GetMapping(value = "getParentProductById")
    public WebResInfo getParentProductById(String productId) {
        log.info("getParentProductById->productId:{}", productId);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            List<ProductDto> parentProductById = materialsRelationshipService.getParentProductById(productId);
            webResInfo.setData(parentProductById);
        } catch (Exception e) {
            log.error("getParentProductById error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 获取子级产品
     *
     * @param productId
     * @return
     */
    @GetMapping(value = "getChildProductById")
    public WebResInfo getChildProductById(String productId) {
        log.info("getChildProductById->productId:{}", productId);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            List<ProductDto> childProductById = materialsRelationshipService.getChildProductById(productId);
            webResInfo.setData(childProductById);
        } catch (Exception e) {
            log.error("getChildProductById error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
