package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.EntryLinkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author WangZhiZhou
 * @date 2021/3/10
 */
@RestController
@RequestMapping("/entryLink")
@Slf4j
public class EntryLinkController {

    @Autowired
    private EntryLinkService entryLinkService;

    /**
     * 最近添加链接 查询接口
     *
     * @param createUserId 创建人id
     * @return WebResInfo<List<EntryLinkEntity>>
     */
    @RequestMapping("/queryEntryLinkRecent")
    public WebResInfo<List<EntryLinkEntity>> queryEntryLinkRecentList(String createUserId) {
        if (StringUtils.isEmpty(createUserId)) {
            return new WebResInfo(WebResCode.Null_Field_Result, null, "方法入参为空");
        }
        log.info("queryEntryLinkRecentlyList -> param:{}", createUserId);
        try {
            List<EntryLinkEntity> list = entryLinkService.queryEntryLinkRecentlyList(createUserId);
            return new WebResInfo(WebResCode.Successful, list, "查询成功");
        } catch (Exception e) {
            log.error("queryEntryLinkRecentlyList -> Exception:{}", e);
        }
        return new WebResInfo(WebResCode.Server_Bug_Exception, null, "查询失败");
    }

    /**
     * 内部链接 查询接口
     *
     * @param var
     * @return
     */
    @RequestMapping("/queryEntryLinkInner")
    public WebResInfo<List<EntryLinkEntity>> queryEntryLinkInnerList(String var) {
        log.info("queryEntryLinkRecentlyList -> param:{}", var);
        try {
            List<EntryLinkEntity> list = entryLinkService.queryEntryLinkInnerList(var);
            return new WebResInfo(WebResCode.Successful, list, "查询成功");
        } catch (Exception e) {
            log.error("queryEntryLinkRecentlyList -> Exception:{}", e);
        }
        return new WebResInfo(WebResCode.Server_Bug_Exception, null, "查询失败");
    }

    /**
     * 单个或批量添加词条链接功能
     *
     * @param entryLinkList EntryLinkEntity列表
     * @return WebResInfo
     */
    @RequestMapping("/addEntryLink")
    public WebResInfo addEntryLinkList(@RequestBody List<EntryLinkEntity> entryLinkList) {
        if (CollectionUtils.isEmpty(entryLinkList)) {
            return new WebResInfo(WebResCode.Null_Field_Result, null, "方法入参为空");
        }
        log.info("addEntryLinkList -> param:{}", entryLinkList);
        try {
            Integer count = entryLinkService.addEntryLinkList(entryLinkList);
            if (count > 0) {
                return new WebResInfo(WebResCode.Successful, null, "数据添加成功");
            }
        } catch (Exception e) {
            log.error("addEntryLinkList -> Exception:{}", e);
        }
        return new WebResInfo(WebResCode.Server_Bug_Exception, null, "添加失败");

    }

    /**
     * 根据词条id删除词条链接
     *
     * @param entryId 词条id
     * @return WebResInfo
     */
    @DeleteMapping("/deleteEntryLink")
    public WebResInfo deleteEntryLinkList(String entryId) {
        if (StringUtils.isEmpty(entryId)) {
            return new WebResInfo(WebResCode.Null_Field_Result, null, "方法入参为空");
        }
        log.info("deleteEntryLinkList -> param:{}", entryId);
        try {
            entryLinkService.deleteEntryLinkList(entryId);
            return new WebResInfo(WebResCode.Successful, null, "删除成功");
        } catch (Exception e) {
            log.error("deleteEntryLinkList -> Exception:{}", e);
        }
        return new WebResInfo(WebResCode.Server_Bug_Exception, null, "删除失败");
    }
}
