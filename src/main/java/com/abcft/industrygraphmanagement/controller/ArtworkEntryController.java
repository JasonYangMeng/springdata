package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.condition.ArtworkEntryCondition;
import com.abcft.industrygraphmanagement.model.dto.ArtworkIndustryDto;
import com.abcft.industrygraphmanagement.model.dto.IndustryToCompanyDto;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.ArtworkEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/24 10:40
 * 产品工艺图
 */
@RestController
@Slf4j
@RequestMapping("artworkEntry")
public class ArtworkEntryController {

    @Autowired
    private ArtworkEntryService artworkEntryService;

    /**
     * 获取生产工艺图信息
     *
     * @return
     */
    @GetMapping(value = "getArtworkEntryList")
    public WebResInfo getArtworkEntryList(String artworkEntryId) {
        log.info("getArtworkEntryList->{}", artworkEntryId);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            ArtworkEntryCondition artworkEntryList = artworkEntryService.getArtworkEntryList(artworkEntryId);
            webResInfo.setData(artworkEntryList);
        } catch (Exception e) {
            log.error("getArtworkEntryList error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }


    /**
     * 获取生产工艺图
     *
     * @return
     */
    @GetMapping(value = "getArtworkGraph")
    public WebResInfo getArtworkGraph(String productEntryId, @RequestParam(name = "level", required = false) Integer level) {
        log.info("getArtworkGraph->{}", productEntryId);
        WebResInfo webResInfo = new WebResInfo();
        try {
            if (level == null || level < 1 || level > 10) {
                level = 6;
            }
            webResInfo.setCode(WebResCode.Successful);
            List<IndustryToCompanyDto> artworkGraphList = artworkEntryService.getArtworkGraphList(productEntryId, level);
            webResInfo.setData(artworkGraphList);
        } catch (Exception e) {
            log.error("getArtworkGraph error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 获取产业链对应生产工艺图
     *
     * @return
     */
    @GetMapping(value = "getArtworkIndustryGraph")
    public WebResInfo getArtworkIndustryGraph(String productEntryId, @RequestParam(name = "level", required = false) Integer level) {
        log.info("getArtworkIndustryGraph->{}", productEntryId);
        WebResInfo webResInfo = new WebResInfo();
        try {
            if (level == null || level < 1 || level > 10) {
                level = 6;
            }
            webResInfo.setCode(WebResCode.Successful);
            List<ArtworkIndustryDto> artworkIndustryGraph = artworkEntryService.getArtworkIndustryGraph(productEntryId, level);
            webResInfo.setData(artworkIndustryGraph);
        } catch (Exception e) {
            log.error("getArtworkIndustryGraph error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }


    /**
     * 修改生产工艺图
     *
     * @return
     */
    @PostMapping(value = "updateArtworkGraph")
    public WebResInfo updateArtworkGraph(@RequestBody ArtworkEntryCondition artworkEntryCondition) {
        log.info("updateArtworkGraph->{}", artworkEntryCondition);
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            boolean b = artworkEntryService.updateArtworkEntry(artworkEntryCondition);
            webResInfo.setData(b);
        } catch (Exception e) {
            log.error("updateArtworkGraph error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
