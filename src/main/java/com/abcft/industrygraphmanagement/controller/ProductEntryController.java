package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.condition.QueryProductCondition;
import com.abcft.industrygraphmanagement.model.dto.ProductEntryParamDto;
import com.abcft.industrygraphmanagement.model.dto.ProductEntryResultDto;
import com.abcft.industrygraphmanagement.model.entity.ProductGenealogyEntity;
import com.abcft.industrygraphmanagement.model.node.AscriptionRelationship;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.ProductEntryService;
import com.abcft.industrygraphmanagement.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/11
 */
@RestController
@RequestMapping(value = "productEntry")
@Slf4j
public class ProductEntryController {

    @Autowired
    private ProductEntryService productEntryService;

    /**
     * 产品词条业务添加、编辑功能接口
     *
     * @param paramDto 入参对象ProductToProductDto列表
     * @return WebResInfo
     */
    @PostMapping(value = "addOrUpdateProductEntry")
    public WebResInfo addOrUpdateProductEntry(@RequestBody ProductEntryParamDto paramDto) {
        log.info("addOrUpdateProductEntry -> param:{}", paramDto);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            String s = productEntryService.addOrUpdateProductEntry(paramDto);
            webResInfo.setData(s);
        } catch (Exception e) {

            log.error("addOrUpdateProductEntry -> Exception:", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 创建产品词条的数据回显接口
     * 点击创建产品词条按钮后调用该接口
     * 如果产品在mysql中没有，则在mysql中创建该产品，审核状态为待审核，
     * 然后调用数据回显接口
     *
     * @param productEntryId 创建产品词条的id
     * @return WebResInfo
     */
    @GetMapping(value = "queryProductEntryByTypeAndName")
    public WebResInfo<ProductEntryResultDto> queryProductEntryByTypeAndName(String productEntryId) {
        WebResInfo<ProductEntryResultDto> webResInfo = new WebResInfo<>();


        log.info("queryProductEntryByTypeAndName -> param:{产品词条的id：{}}", productEntryId);
        try {
            webResInfo.setCode(WebResCode.Successful);
            ProductEntryResultDto dto = productEntryService.queryProductEntryByTypeAndName(productEntryId);
            webResInfo.setData(dto);
        } catch (Exception e) {
            log.error("queryProductEntryByTypeAndName -> Exception:", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 通过产品词条名字查询产品词条
     *
     * @param productEntryNodeName 产品词条名字
     * @return List<ProductEntryNode>
     */
    @GetMapping("/queryProductEntryNode")
    public WebResInfo queryProductEntryNode(String productEntryNodeName) {
        log.info("queryProductEntryNode -> 入参:{}", productEntryNodeName);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            List<ProductEntryNode> list = productEntryService.queryProductEntryNode(productEntryNodeName);
            webResInfo.setData(list);
        } catch (Exception e) {
            log.error("queryProductEntryNode -> Exception:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 通过产品词条名字查询产品词条
     *
     * @param productEntryNodeName 产品词条名字
     * @return ProductEntryNode
     */
    @GetMapping("/queryProductEntry")
    public WebResInfo queryProductEntry(String productEntryNodeName) {
        log.info("queryProductEntry -> 入参:{}", productEntryNodeName);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            ProductEntryNode productEntryNode = productEntryService.queryProductEntry(productEntryNodeName);
            webResInfo.setData(productEntryNode);
        } catch (Exception e) {
            log.error("queryProductEntry -> Exception:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 查询产品归属关系列表
     *
     * @param queryProductCondition 查询条件对象QueryProductCondition
     * @return WebResInfo<List       <       AscriptionRelationship>>
     */
    @PostMapping("/queryAscription")
    public WebResInfo<List<AscriptionRelationship>> queryAscriptionRelationship(@RequestBody QueryProductCondition queryProductCondition) {
        WebResInfo webResInfo = new WebResInfo();
        String productEntryId = queryProductCondition.getProductEntryId();
        String type = queryProductCondition.getType();
        if (StringUtils.isEmpty(productEntryId) || StringUtils.isEmpty(type)) {
            webResInfo.setCode(WebResCode.Format_Parameter);
            webResInfo.setMessage("入参错误，入参必须有产品词条id和产品词条区分类型");
        }
        log.info("queryAscriptionRelationship -> param:{}", queryProductCondition);
        try {
            webResInfo.setCode(WebResCode.Successful);
            List<AscriptionRelationship> ascriptionRelationshipList = productEntryService.queryAscriptionRelationship(productEntryId, type);
            webResInfo.setData(ascriptionRelationshipList);
        } catch (Exception e) {
            log.error("queryAscriptionRelationship -> Exception:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 查询产品族谱信息
     *
     * @param productEntryId 当前入参产品词条id
     * @return WebResInfo<ProductGenealogyEntity>
     */
    @GetMapping("/queryProductGenealogy")
    public WebResInfo<ProductGenealogyEntity> queryProductGenealogy(String productEntryId) {
        if (StringUtils.isEmpty(productEntryId)) {
            log.error("queryProductGenealogy(String productEntryId) , 方法入参为空");
            return new WebResInfo(WebResCode.Null_Field_Result, null, "方法入参为空");
        }
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            ProductGenealogyEntity entity = productEntryService.queryProductGenealogy(productEntryId);
            webResInfo.setData(entity);
        } catch (Exception e) {
            log.error("queryProductGenealogy -> Exception:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
