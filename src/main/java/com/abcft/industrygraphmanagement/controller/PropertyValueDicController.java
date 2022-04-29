package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.node.PropertyValueDicNode;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.PropertyValueDicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/4/19
 */
@RestController
@Slf4j
@RequestMapping("propertyValueDic")
public class PropertyValueDicController {

    private static final String INFO_STR = "方法名：{}, 方法入参：{}";
    private static final String ERROR_STR = "方法名：{},  error：{}";
    @Autowired
    private PropertyValueDicService propertyValueDicService;

    /**
     * 属性值字典维护
     *
     * @param node 节点
     * @return WebResInfo
     */
    @PostMapping(value = "/updatePropertyValueDic")
    public WebResInfo updatePropertyValueDic(@RequestBody PropertyValueDicNode node) {
        String methodName = "updatePropertyValueDic";
        log.info(INFO_STR, methodName, node);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            propertyValueDicService.updatePropertyValueDic(node);
        } catch (Exception e) {
            log.error(ERROR_STR, methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 模糊查询属性值字典
     *
     * @param name 名字
     * @return WebResInfo
     */
    @GetMapping(value = "/getPropertyValueDicList")
    public WebResInfo getPropertyValueDicList(String name, Integer pageNum, Integer pageSize) {
        String methodName = "getPropertyValueDicByPage";
        log.info(INFO_STR, methodName, name);
        WebResInfo<Paged<PropertyValueDicNode>> webResInfo = new WebResInfo<>();
        try {
            webResInfo.setCode(WebResCode.Successful);
            Paged<PropertyValueDicNode> paged = propertyValueDicService.getPropertyValueDicList(name, pageNum, pageSize);
            webResInfo.setData(paged);
        } catch (Exception e) {
            log.error(ERROR_STR, methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 删除属性字典
     *
     * @param id uuid列表
     * @return WebResInfo
     */
    @PostMapping(value = "/deletePropertyValueDicList")
    public WebResInfo deletePropertyValueDicList(@RequestBody List<String> id) {
        String methodName = "deletePropertyValueDicList";
        log.info(INFO_STR, methodName, id);
        WebResInfo webResInfo = new WebResInfo<>();
        try {
            webResInfo.setCode(WebResCode.Successful);
            propertyValueDicService.deletePropertyValueDicList(id);
        } catch (Exception e) {
            log.error(ERROR_STR, methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
