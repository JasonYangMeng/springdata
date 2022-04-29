package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.node.PropertyValueDicNode;
import com.abcft.industrygraphmanagement.model.node.RelationshipDicNode;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.RelationshipDicService;
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
@RequestMapping("relationshipDic")
public class RelationshipDicController {
    private static final String INFO_STR = "方法名：{}, 方法入参：{}";
    private static final String ERROR_STR = "方法名：{},  error：{}";

    @Autowired
    private RelationshipDicService relationshipDicService;

    /**
     * 关系字典维护
     *
     * @param node 节点
     * @return WebResInfo
     */
    @PostMapping(value = "/updateRelationshipDic")
    public WebResInfo updateRelationshipDic(@RequestBody RelationshipDicNode node) {
        String methodName = "updateRelationshipDic";
        log.info(INFO_STR, methodName, node);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            relationshipDicService.updateRelationshipDic(node);
        } catch (Exception e) {
            log.error(ERROR_STR, methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 模糊查询关系字典
     *
     * @param name 名字
     * @return WebResInfo
     */
    @GetMapping(value = "/getRelationshipDicList")
    public WebResInfo getRelationshipDicList(String name, Integer pageNum, Integer pageSize) {
        String methodName = "getRelationshipDicList";
        log.info("方法名:{} 方法入参: name:{}, pageNum:{}, pageSize:{}", methodName, name, pageNum, pageSize);
        WebResInfo<Paged<RelationshipDicNode>> webResInfo = new WebResInfo<>();
        try {
            webResInfo.setCode(WebResCode.Successful);
            Paged<RelationshipDicNode> paged = relationshipDicService.getRelationshipDicList(name, pageNum, pageSize);
            webResInfo.setData(paged);
        } catch (Exception e) {
            log.error(ERROR_STR, methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 删除关系字典
     *
     * @param id uuid列表
     * @return WebResInfo
     */
    @PostMapping(value = "/deleteRelationshipDicList")
    public WebResInfo deleteRelationshipDicList(@RequestBody List<String> id) {
        String methodName = "deleteRelationshipDicList";
        log.info(INFO_STR, methodName, id);
        WebResInfo webResInfo = new WebResInfo<>();
        try {
            webResInfo.setCode(WebResCode.Successful);
            relationshipDicService.deleteRelationshipDicList(id);
        } catch (Exception e) {
            log.error(ERROR_STR, methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
