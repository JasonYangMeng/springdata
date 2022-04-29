package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.EntryInfoSourceService;
import com.abcft.industrygraphmanagement.service.EntryLinkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/10
 */
@RestController
@RequestMapping("/entryInfoSource")
@Slf4j
public class EntryInfoSourceController {

    @Autowired
    private EntryInfoSourceService entryInfoSourceService;

    /**
     * 新增词条信息源数据接口
     *
     * @param entryInfoSourceList EntryInfoSourceEntity列表 词条信息来源对象
     * @return WebResInfo
     */
    @RequestMapping("/addEntryInfoSource")
    public WebResInfo addEntryInfoSource(@RequestBody List<EntryInfoSourceEntity> entryInfoSourceList) {
        if (null == entryInfoSourceList) {
            return new WebResInfo(WebResCode.Null_Field_Result, null, "入参为空");
        }
        log.info("addEntryInfoSource -> condition:{}", entryInfoSourceList);
        try {
            Integer count = entryInfoSourceService.addEntryInfoSource(entryInfoSourceList);
            if (count > 0) {
                return new WebResInfo(WebResCode.Successful, null, "新增词条信息源数据成功");
            }
        } catch (Exception e) {
            log.error("addEntryInfoSource -> Exception:{}", e);
        }
        return new WebResInfo(WebResCode.Null_Result, null, "新增词条信息源数据失败");
    }

    /**
     * 根据词条id来删除词条信息来源数据
     *
     * @param entryId 词条id
     * @return WebResInfo
     */
    @RequestMapping("/deleteEntryInfoSource")
    public WebResInfo deleteEntryInfoSource(String entryId) {
        if (StringUtils.isEmpty(entryId)) {
            return new WebResInfo(WebResCode.Null_Field_Result, null, "入参为空");
        }
        log.info("addEntryInfoSource -> param:{}", entryId);
        try {
            // 删除词条信息来源数据
            Integer count = entryInfoSourceService.deleteEntryInfoSource(entryId);
            if (count > 0) {
                return new WebResInfo(WebResCode.Successful, null, "删除词条信息源数据成功");
            }
        } catch (Exception e) {
            log.error("addEntryInfoSource -> Exception:{}", e);
        }
        return new WebResInfo(WebResCode.Null_Result, null, "新增词条信息源数据失败");
    }

}
