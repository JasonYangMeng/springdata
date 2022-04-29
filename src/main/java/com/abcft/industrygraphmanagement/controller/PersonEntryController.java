package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.dto.PersonEntryParamDto;
import com.abcft.industrygraphmanagement.model.dto.PersonEntryResultDto;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.PersonEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 人物词条controller层
 *
 * @author WangZhiZhou
 * @date 2021/3/31
 */
@RestController
@RequestMapping("personEntry")
@Slf4j
public class PersonEntryController {
    @Autowired
    private PersonEntryService personEntryService;

    /**
     * 创建人物词条
     *
     * @param personEntryResult 创建人物词条入参对象
     * @return WebResInfo
     */
    @PostMapping("/createPersonEntry")
    public WebResInfo createPersonEntry(@RequestBody PersonEntryResultDto personEntryResult) {
        WebResInfo webResInfo = new WebResInfo();
        String methodName = "createPersonEntry";
        log.info("方法名：{} -> 入参:{}", methodName, personEntryResult);
        try {
            webResInfo.setCode(WebResCode.Successful);
            personEntryService.createPersonEntry(personEntryResult);
            webResInfo.setMessage("创建成功！");
        } catch (Exception e) {
            log.error("方法名：{} -> Exception:", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 根据人物词条id来回显创建人物词条数据
     * @param personEntryId 人物词条id
     * @return WebResInfo
     */
    @GetMapping("/queryPersonEntrySource")
    public WebResInfo queryPersonEntrySource(String personEntryId) {
        WebResInfo<PersonEntryResultDto> webResInfo = new WebResInfo<>();
        String methodName = "queryPersonEntrySource";
        log.info("方法名：{} -> 入参:{}", methodName, personEntryId);
        try {
            PersonEntryResultDto personEntryResult = personEntryService.queryPersonEntrySource(personEntryId);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(personEntryResult);
            log.info("方法名：{} -> 入参:{} , 返回结果：{}", methodName, personEntryId, personEntryResult);
        } catch (Exception e) {
            log.error("方法名：{} -> Exception:", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
