package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.condition.PageCondition;
import com.abcft.industrygraphmanagement.model.node.TopSearchNode;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.TopSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Created by YangMeng on 2021/6/17 11:15
 */
@RestController
@RequestMapping("topSearch")
@Slf4j
public class TopSearchController {

    @Autowired
    private TopSearchService topSearchService;

    /**
     * 创建banner
     *
     * @param topSearchNode
     * @return WebResInfo
     */
    @PostMapping("/saveTopSearch")
    public WebResInfo saveTopSearch(@RequestBody TopSearchNode topSearchNode) {
        WebResInfo webResInfo = new WebResInfo();

        try {
            log.info("方法名：{}，入参：{}", "saveTopSearch", topSearchNode);
            topSearchService.saveNode(topSearchNode);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", "saveBanner", e);
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
            Paged<TopSearchNode> pagedList = topSearchService.getPagedList(pageCondition);
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
            topSearchService.deleteNode(uuids);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 数据回显
     *
     * @param uuid
     * @return WebResInfo
     */
    @GetMapping("/getTopSearchById")
    public WebResInfo getTopSearchById(String uuid) {
        WebResInfo webResInfo = new WebResInfo<>();
        if (StringUtils.isEmpty(uuid)) {
            webResInfo.setCode(WebResCode.Format_Parameter);
            webResInfo.setMessage("入参不能为空！");
            return webResInfo;
        }
        try {
            log.info("方法名：{}，入参：{}", "getTopSearchById", uuid);
            TopSearchNode entryById = topSearchService.getEntryById(uuid);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(entryById);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", "getTopSearchById", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 上下移动数据 1上移 0 下移
     *
     * @param uuid
     * @return WebResInfo
     */
    @GetMapping("/updateOrderNum")
    public WebResInfo updateOrderNum(String uuid, String upOrDown) {
        WebResInfo webResInfo = new WebResInfo<>();
        String methodName = "updateOrderNum";
        try {
            log.info("方法名：{}，入参：{}", methodName, uuid);
            topSearchService.sortById(uuid, upOrDown);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 修改状态
     *
     * @param uuids
     * @return WebResInfo
     */
    @GetMapping("/updateStatus")
    public WebResInfo updateStatus(String[] uuids, String issue) {
        WebResInfo webResInfo = new WebResInfo<>();
        String methodName = "updateStatus";
        try {
            log.info("方法名：{}，入参：{}", methodName, uuids);
            topSearchService.updateStatus(uuids, issue);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
