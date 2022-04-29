package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.condition.PageCondition;
import com.abcft.industrygraphmanagement.model.node.DataSourceNode;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/7/5 14:13
 */
@RestController
@RequestMapping("dataSource")
@Slf4j
public class DataSourceController {
    @Autowired
    private DataSourceService dataSourceService;


    /**
     * 创建
     *
     * @param dataSourceNode
     * @return WebResInfo
     */
    @PostMapping("/save")
    public WebResInfo save(@RequestBody DataSourceNode dataSourceNode) {
        WebResInfo webResInfo = new WebResInfo();

        try {
            log.info("方法名：{}，入参：{}", "save", dataSourceNode);
            dataSourceService.saveNode(dataSourceNode);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", "save", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 获取分页数据
     *
     * @param pageCondition
     * @return
     */
    @GetMapping("/getPagedList")
    public WebResInfo getPagedList(PageCondition pageCondition) {
        WebResInfo webResInfo = new WebResInfo();
        try {
            log.info("方法名：{}，入参：{}", "getPagedList", pageCondition);
            Paged<DataSourceNode> pagedList = dataSourceService.getPagedList(pageCondition);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(pagedList);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", "getPagedList", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 获取所有数据
     *
     * @param
     * @return
     */
    @GetMapping("/getAll")
    public WebResInfo getAll() {
        WebResInfo webResInfo = new WebResInfo();
        try {
            log.info("方法名：{}，", "getAll");
            List<DataSourceNode> allList = dataSourceService.getAllList();
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(allList);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", "getAll", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 删除数据
     *
     * @param uuids
     * @return WebResInfo
     */
    @GetMapping("/deleteById")
    public WebResInfo deleteById(String[] uuids) {
        WebResInfo webResInfo = new WebResInfo<>();
        String methodName = "deleteById";
        if (StringUtils.isEmpty(uuids)) {
            webResInfo.setCode(WebResCode.Format_Parameter);
            webResInfo.setMessage("入参不能为空！");
            return webResInfo;
        }
        try {
            log.info("方法名：{}，入参：{}", methodName, uuids);
            dataSourceService.deleteNode(uuids);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

}
