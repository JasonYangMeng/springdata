package com.abcft.industrygraphmanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author Created by YangMeng on 2021/3/16 11:58
 */
@RestController
@RequestMapping("health")
@Slf4j
public class HealthController {
    @GetMapping(value = "index")
    public String getHealth() {
        return new Date().toString();
    }
}
