package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.condition.SysCollectCondition;
import com.abcft.industrygraphmanagement.model.entity.SysSubscribeSettingEntity;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.SysSubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Created by YangMeng on 2021/5/12 15:48
 */
@RestController
@RequestMapping(value = "sysSubscribe")
@Slf4j
public class SysSubscribeController {

    @Autowired
    private SysSubscribeService sysSubscribeService;

    /**
     * 添加收藏
     *
     * @param sysSubscribeSettingEntity
     * @return
     */
    @PostMapping(value = "insert")
    public WebResInfo insert(@RequestBody SysSubscribeSettingEntity sysSubscribeSettingEntity) {
        WebResInfo webResInfo = new WebResInfo();
        try {
            sysSubscribeService.insert(sysSubscribeSettingEntity);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("insert  fail:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
        }
        return webResInfo;
    }

    /**
     * 取消收藏
     *
     * @param entryId
     * @return
     */
    @GetMapping(value = "cancelSubscribe")
    public WebResInfo cancelSubscribe(String entryId) {
        WebResInfo webResInfo = new WebResInfo();
        if (StringUtils.isEmpty(entryId)) {
            webResInfo.setCode(WebResCode.Format_Parameter);
            return webResInfo;
        }
        try {
            sysSubscribeService.delete(entryId);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("cancelCollect  fail:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
        }
        return webResInfo;
    }

    /**
     * 获取分页数据
     *
     * @param sysCollectCondition
     * @return
     */
    @GetMapping(value = "getPagedList")
    public WebResInfo getPagedList(SysCollectCondition sysCollectCondition) {
        log.info("sysCollectCondition:{}", sysCollectCondition);
        WebResInfo webResInfo = new WebResInfo();
        try {
            Paged<SysSubscribeSettingEntity> pagedListByCondition = sysSubscribeService.getPagedListByCondition(sysCollectCondition);
            webResInfo.setData(pagedListByCondition);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("getPagedList  fail:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
        }
        return webResInfo;
    }
}
