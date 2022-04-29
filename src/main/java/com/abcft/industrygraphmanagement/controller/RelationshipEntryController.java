package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.dto.QueryRelationDetailsParamDto;
import com.abcft.industrygraphmanagement.model.dto.RelationshipEntryDto;
import com.abcft.industrygraphmanagement.model.dto.RelationshipEntryParamDto;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.RelationshipEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/24
 */
@RestController
@RequestMapping("/relationshipEntry")
@Slf4j
public class RelationshipEntryController {

    @Autowired
    private RelationshipEntryService relationshipEntryService;

    /**
     * 关系词条创建关系接口
     *
     * @param relationshipEntryParamDto RelationshipEntryParamDto
     * @return webResInfo
     */
    @PostMapping("/createRelationship")
    public WebResInfo createRelationship(@RequestBody RelationshipEntryParamDto relationshipEntryParamDto) {
        String methodName = "createRelationship";
        WebResInfo webResInfo = new WebResInfo();
        if (relationshipEntryParamDto.getRelationshipEntryDto() == null) {
            log.error("接口名：{} -> 方法入参为空", methodName);
            webResInfo.setCode(WebResCode.Invalid_Parameter);
            webResInfo.setMessage("方法入参不能为空！");
            return webResInfo;
        }
        try {
            log.info("接口名：{} -> param:{}", methodName, relationshipEntryParamDto);
            relationshipEntryService.createRelationship(relationshipEntryParamDto);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setMessage("关系词条创建成功！");
        } catch (Exception e) {
            log.error("接口名：{} -> Exception:", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 查询关系详情接口
     *
     * @param queryRelationDetailsDto 查询关系详情入参dto
     * @return 关系详情
     */
    @PostMapping("/queryRelationDetails")
    public WebResInfo queryRelationDetails(@RequestBody QueryRelationDetailsParamDto queryRelationDetailsDto) {
        String methodName = "queryRelationDetails";
        WebResInfo webResInfo = new WebResInfo();
        if (queryRelationDetailsDto == null) {
            log.error("接口名：{} -> 方法入参为空", methodName);
            webResInfo.setCode(WebResCode.Invalid_Parameter);
            webResInfo.setMessage("方法入参不能为空！");
            return webResInfo;
        }
        try {
            log.info("接口名：{} -> param:{}", methodName, queryRelationDetailsDto);
            RelationshipEntryDto relationshipEntryDto =
                    relationshipEntryService.queryRelationDetails(queryRelationDetailsDto);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(relationshipEntryDto);
        } catch (Exception e) {
            log.error("接口名：{} -> Exception:{}", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 通过公司词条id 查询 该公司下的所有产品
     *
     * @param companyEntryId 公司词条id
     * @return List<ProductEntryNode>
     */
    @GetMapping("/queryProducts")
    public WebResInfo queryProductsByCompanyEntryId(String companyEntryId) {
        String methodName = "queryProductsByCompanyEntryId";
        WebResInfo webResInfo = new WebResInfo();
        if (StringUtils.isEmpty(companyEntryId)) {
            log.error("接口名：{} -> 方法入参为空", methodName);
            webResInfo.setCode(WebResCode.Invalid_Parameter);
            webResInfo.setMessage("方法入参不能为空！");
            return webResInfo;
        }
        try {
            log.info("接口名：{} -> param:{}", methodName, companyEntryId);
            List<ProductEntryNode> productList =
                    relationshipEntryService.queryProductsByCompanyEntryId(companyEntryId);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(productList);
        } catch (Exception e) {
            log.error("接口名：{} -> Exception:{}", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
