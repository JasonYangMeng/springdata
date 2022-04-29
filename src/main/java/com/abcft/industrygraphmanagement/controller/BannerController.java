package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.condition.BannerCondition;
import com.abcft.industrygraphmanagement.model.node.BannerNode;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Created by YangMeng on 2021/3/24 10:40
 * banner
 */
@RestController
@RequestMapping("banner")
@Slf4j
public class BannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * 创建banner
     *
     * @param bannerNode
     * @return WebResInfo
     */
    @PostMapping("/saveBanner")
    public WebResInfo saveBanner(@RequestBody BannerNode bannerNode) {
        WebResInfo webResInfo = new WebResInfo();

        try {
            log.info("方法名：{}，入参：{}", "saveBanner", bannerNode);
            bannerService.saveNode(bannerNode);
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
     * @param bannerCondition
     * @return
     */
    @GetMapping("/getPagedList")
    public WebResInfo getPagedList(BannerCondition bannerCondition) {
        WebResInfo webResInfo = new WebResInfo();
        try {
            log.info("方法名：{}，入参：{}", "saveBanner", bannerCondition);
            Paged<BannerNode> pagedList = bannerService.getPagedList(bannerCondition);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(pagedList);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", "saveBanner", e);
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
            bannerService.deleteNode(uuids);
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
    @GetMapping("/getBannerById")
    public WebResInfo getBannerById(String uuid) {
        WebResInfo webResInfo = new WebResInfo<>();
        if (StringUtils.isEmpty(uuid)) {
            webResInfo.setCode(WebResCode.Format_Parameter);
            webResInfo.setMessage("入参不能为空！");
            return webResInfo;
        }
        try {
            log.info("方法名：{}，入参：{}", "getBannerById", uuid);
            BannerNode entryById = bannerService.getEntryById(uuid);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(entryById);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", "getBannerById", e);
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
            bannerService.sortById(uuid, upOrDown);
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
            bannerService.updateStatus(uuids, issue);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
