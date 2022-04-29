package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.condition.MyCreateEntryCondition;
import com.abcft.industrygraphmanagement.model.dto.MyCreateEntryDto;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.MyEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 我的词条
 *
 * @author WangZhiZhou
 * @date 2021/5/18
 */
@RestController
@RequestMapping("myEntry")
@Slf4j
public class MyEntryController {

    @Autowired
    private MyEntryService myEntryService;

    /**
     * 我创建的词条
     *
     * @param condition 入参
     * @return WebResInfo
     */
    @PostMapping("/getMyCreateEntry")
    public WebResInfo getMyCreateEntry(@RequestBody MyCreateEntryCondition condition) {
        WebResInfo<Paged<MyCreateEntryDto>> webResInfo = new WebResInfo<>();
        String methodName = "getMyCreateEntry";
        try {
            Paged<MyCreateEntryDto> page = myEntryService
                    .getMyEntry(condition, "createUserId");
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(page);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", methodName , e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 我编辑的词条
     *
     * @param condition 入参
     * @return WebResInfo
     */
    @PostMapping("/getMyUpdateEntry")
    public WebResInfo getMyUpdateEntry(@RequestBody MyCreateEntryCondition condition) {
        WebResInfo<Paged<MyCreateEntryDto>> webResInfo = new WebResInfo<>();
        String methodName = "getMyUpdateEntry";
        try {
            Paged<MyCreateEntryDto> page = myEntryService
                    .getMyEntry(condition, "modifyUserId");
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(page);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", methodName , e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
