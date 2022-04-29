package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.condition.SysCollectCondition;
import com.abcft.industrygraphmanagement.model.entity.SysCollectSettingEntity;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.SysCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Created by YangMeng on 2021/5/12 15:48
 */
@RestController
@RequestMapping(value = "sysCollect")
@Slf4j
public class SysCollectController {

    @Autowired
    private SysCollectService sysCollectService;

    /**
     * 添加收藏
     *
     * @param sysCollectSettingEntity
     * @return
     */
    @PostMapping(value = "insert")
    public WebResInfo insert(@RequestBody SysCollectSettingEntity sysCollectSettingEntity) {
        WebResInfo webResInfo = new WebResInfo();
        try {
            sysCollectService.insert(sysCollectSettingEntity);
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
    @GetMapping(value = "cancelCollect")
    public WebResInfo cancelCollect(String entryId) {
        WebResInfo webResInfo = new WebResInfo();
        if (StringUtils.isEmpty(entryId)) {
            webResInfo.setCode(WebResCode.Format_Parameter);
            return webResInfo;
        }
        try {
            sysCollectService.delete(entryId);
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
            Paged<SysCollectSettingEntity> pagedListByCondition = sysCollectService.getPagedListByCondition(sysCollectCondition);
            webResInfo.setData(pagedListByCondition);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("getPagedList  fail:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
        }
        return webResInfo;
    }
}
