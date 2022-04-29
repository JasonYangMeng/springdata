package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.entity.PersonEntity;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/4/1
 */
@RestController
@RequestMapping("person")
@Slf4j
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/queryPersonByName")
    public WebResInfo queryPersonByName(String name) {
        WebResInfo<List<PersonEntity>> webResInfo = new WebResInfo<>();
        String methodName = "queryPersonByName";
        log.info("方法名：{}，入参：{}", methodName, name);
        try {
            List<PersonEntity> list = personService.queryPersonByName(name);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(list);
        }catch (Exception e) {
            log.error("方法名:{}, 入参：{}, Exception:", methodName, name, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
