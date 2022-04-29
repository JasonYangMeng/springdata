package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.entity.ProductEntity;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/9
 */
@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 产品词条名称模糊查询,展示前十条
     *
     * @param productName 查询名称参数
     * @return WebResInfo<List < ProductEntryEntity>> 产品词条对象列表
     */
    @GetMapping("/queryProductNameListByName")
    public WebResInfo<List<ProductEntity>> queryProductNameListByName(String productName) {
        log.info("queryEntryNameList -> condition:{productName:{}}", productName);
        List<ProductEntity> list = new ArrayList<>();
        try {
            list = productService.queryProductNameListByName(productName);
        } catch (Exception e) {
            log.error("queryEntryNameList -> exception:{}", e);
        }
        return new WebResInfo(WebResCode.Successful, list, null);
    }

    /**
     * 根据产品区分类型和产品名称来检索产品
     *
     * @param type        产品区分类型
     * @param productName 查询名称参数
     * @return WebResInfo<List < ProductEntryEntity>> 产品词条对象列表
     */
    @GetMapping("/queryByTypeAndName")
    public WebResInfo<List<ProductEntity>> queryProductNameListByTypeAndName(String type, String productName) {
        log.info("queryEntryNameList -> condition:{type:{}, productName:{}}", type, productName);
        List<ProductEntity> list = new ArrayList<>();
        try {
            list = productService.queryProductNameListByTypeAndName(type, productName);
        } catch (Exception e) {
            log.error("queryEntryNameList -> exception:{}", e);
            return new WebResInfo(WebResCode.Server_Bug_Exception, list, e.getMessage());
        }
        return new WebResInfo(WebResCode.Successful, list, null);
    }

    /**
     * 产品词条名称精确查询
     *
     * @param name 产品词条名称
     * @return WebResInfo<List < ProductEntryEntity>> 产品词条对象列表
     */
    @GetMapping("/queryProductNameAccurate")
    public WebResInfo<List<ProductEntity>> queryProductNameAccurateList(String name) {
        if (StringUtils.isBlank(name)) {
            return new WebResInfo(WebResCode.Null_Field_Result, null, "方法入参为空！");
        }
        log.info("queryProductNameAccurateList -> condition:{}", name);
        ProductEntity entity = new ProductEntity();
        try {
            entity = productService.queryProductNameAccurateList(name);
        } catch (Exception e) {
            log.error("queryProductNameAccurateList -> exception:{}", e);
        }
        return new WebResInfo(WebResCode.Successful, entity, null);
    }
}
