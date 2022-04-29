package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.entity.AllSysDictionaryResultEntity;
import com.abcft.industrygraphmanagement.model.entity.SysDictionaryEntity;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.SysDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/3 11:31
 */
@RestController
@RequestMapping(value = "sysDictionary")
@Slf4j
public class SysDictionaryController {

    @Autowired
    private SysDictionaryService dictionaryService;

    /**
     * 获取字典数据
     *
     * @return WebResInfo 字典数据列表
     */
    @GetMapping(value = "getAllSysDictionary")
    public WebResInfo getAllSysDictionaryList() {
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            List<AllSysDictionaryResultEntity> list = dictionaryService.getAllSysDictionaryList();
            webResInfo.setData(list);

        } catch (Exception e) {
            log.error("getAllSysDictionaryList error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 通过关系字典id 获取关系词条的关系类型
     *
     * @param id 关系字典id
     * @return WebResInfo 关系类型字典数据列表
     */
    @GetMapping(value = "getRelationSysDictionary")
    public WebResInfo getRelationSysDictionaryList(String id) {
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            List<SysDictionaryEntity> list = dictionaryService.getRelationSysDictionaryList(id);
            webResInfo.setData(list);
        } catch (Exception e) {
            log.error("getRelationSysDictionaryList error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 通过职位名字 获取职位
     *
     * @param name 职位名字
     * @return WebResInfo 关系类型字典数据列表
     */
    @GetMapping(value = "getPostList")
    public WebResInfo getPostSysDictionaryList(String name) {
        WebResInfo<List<String>> webResInfo = new WebResInfo<>();
        try {
            webResInfo.setCode(WebResCode.Successful);
            List<String> list = dictionaryService.getPostSysDictionaryList(name);
            webResInfo.setData(list);
        } catch (Exception e) {
            log.error("getPostSysDictionaryList error:", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 单个或批量新增词条类型字典
     *
     * @param sysDictionaryList List<SysDictionaryEntity> 字典表实体类列表
     * @return WebResInfo
     */
    @PostMapping("/addEntryList")
    public WebResInfo addEntryList(@RequestBody List<SysDictionaryEntity> sysDictionaryList) {
        log.info("addEntryList -> condition:{}", sysDictionaryList);
        try {
            dictionaryService.addEntryList(sysDictionaryList);
            return new WebResInfo(WebResCode.Successful, null, "添加词条类型成功！");
        } catch (Exception e) {
            log.error("addEntryList -> exception:{}", e);
        }
        return new WebResInfo(WebResCode.Server_Bug_Exception, null, "添加词条类型失败");
    }

    /**
     * 根据id删除单个词条类型字典
     *
     * @param id 词条类型UUID
     * @return WebResInfo
     */
    @DeleteMapping("/delEntry")
    public WebResInfo delEntry(String id) {
        log.info("delEntry -> condition:{}", id);
        try {
            dictionaryService.delEntry(id);
            return new WebResInfo(WebResCode.Successful, null, "删除词条类型成功！");
        } catch (Exception e) {
            log.error("delEntry -> exception:{}", e);
        }
        return new WebResInfo(WebResCode.Server_Bug_Exception, null, "删除词条类型失败！");
    }

    /**
     * 更新单个词条类型字典
     *
     * @param sysDictionary SysDictionaryEntity
     * @return WebResInfo
     */
    @RequestMapping("/updateEntry")
    public WebResInfo updateEntry(@RequestBody SysDictionaryEntity sysDictionary) {
        if (null == sysDictionary){
            return new WebResInfo(WebResCode.Null_Field_Result, null, "SysDictionaryEntity 入参为空！");
        }
        log.info("updateEntry -> condition:{}", sysDictionary);
        try {
            dictionaryService.updateEntry(sysDictionary);
            return new WebResInfo(WebResCode.Successful, null, "更新词条类型成功！");
        } catch (Exception e) {
            log.error("updateEntry -> Exception:{}", e);
        }
        return new WebResInfo(WebResCode.Server_Bug_Exception, null, "更新词条类型失败！");
    }

}
