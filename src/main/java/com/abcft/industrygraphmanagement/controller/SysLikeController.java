package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.entity.SysLikeEntity;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.SysLikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Created by YangMeng on 2021/5/12 15:48
 */
@RestController
@RequestMapping(value = "sysLike")
@Slf4j
public class SysLikeController {

    @Autowired
    private SysLikeService sysLikeService;

    /**
     * 添加点赞
     *
     * @param sysLikeEntity
     * @return
     */
    @PostMapping(value = "insert")
    public WebResInfo insert(@RequestBody SysLikeEntity sysLikeEntity) {
        WebResInfo webResInfo = new WebResInfo();
        log.info("insert:{}", sysLikeEntity);
        try {
            int insert = sysLikeService.insert(sysLikeEntity);
            webResInfo.setData(insert);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("insert  fail:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
        }
        return webResInfo;
    }

    /**
     * 取消点赞
     *
     * @param entryId
     * @return
     */
    @GetMapping(value = "cancelLike")
    public WebResInfo cancelLike(String entryId) {
        WebResInfo webResInfo = new WebResInfo();
        log.info("cancelLike:{}", entryId);
        if (StringUtils.isEmpty(entryId)) {
            webResInfo.setCode(WebResCode.Format_Parameter);
            return webResInfo;
        }
        try {
            int delete = sysLikeService.delete(entryId);
            webResInfo.setData(delete);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("cancelCollect  fail:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
        }
        return webResInfo;
    }
}
