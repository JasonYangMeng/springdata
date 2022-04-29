package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.entity.SysIndustryEntity;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.SysIndustryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <>
 * <功能描述>
 *
 * @date 2021/3/9
 */
@RestController
@RequestMapping("/sysIndustry")
@Slf4j
public class SysIndustryController {

    private static final String PATH = "E:\\workSpace\\industry-graph-management\\src\\main\\test\\";
    @Autowired
    private SysIndustryService sysIndustryService;

    /**
     * 通过行业excel模板文件导入行业数据
     *
     * @param fileName 文件名
     * @return WebResInfo
     */
    @GetMapping("/addIndustry")
    public WebResInfo addIndustryList(String fileName) {
        String message = "";
        try {
            String filePath = PATH + fileName;
            sysIndustryService.addIndustryList(filePath);
            message = "行业数据添加成功！";
        } catch (Exception e) {
            log.error("addIndustryList -> exception:{}", e);
            message = "行业数据添加失败！";
        }
        return new WebResInfo(WebResCode.Successful, null, message);
    }

    /**
     * 查询所有行业信息
     *
     * @return 所有行业信息 三级行业
     */
    @RequestMapping("/querySysIndustry")
    public WebResInfo<List<SysIndustryEntity>> querySysIndustryList() {
        List<SysIndustryEntity> list;
        try {
            list = sysIndustryService.querySysIndustryList();
            return new WebResInfo(WebResCode.Successful, list, "查询所有行业信息成功");
        } catch (Exception e) {
            log.error("querySysIndustryList -> exception{}", e);
            return new WebResInfo(WebResCode.Server_Bug_Exception, null, e.getMessage());
        }
    }

    /**
     * 根据行业名称模糊查询行业信息
     *
     * @param name 行业名称
     * @return 行业信息
     */
    @RequestMapping("/querySysIndustryListByName")
    public WebResInfo querySysIndustryListByName(String name) {
        WebResInfo<List<SysIndustryEntity>> webResInfo = new WebResInfo<>();
        String methodName = "querySysIndustryListByName";
        try {
            log.info("方法名：{}，入参：{}", methodName, name);
            List<SysIndustryEntity> list = sysIndustryService.querySysIndustryListByName(name);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(list);
        } catch (Exception e) {
            log.error("方法名：{}，入参：{} -> exception{}", methodName, name, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 根据行业名称和行业级别模糊查询行业信息
     *
     * @param name 行业名称
     * @param level 行业级别
     * @return 行业信息
     */
    @RequestMapping("/querySysIndustryListByParam")
    public WebResInfo querySysIndustryListByNameAndLevel(String name, String level) {
        WebResInfo<List<SysIndustryEntity>> webResInfo = new WebResInfo<>();
        String methodName = "querySysIndustryListByNameAndLevel";
        try {
            log.info("方法名：{}，入参：name:{}, level:{}", methodName, name, level);
            List<SysIndustryEntity> list = sysIndustryService.querySysIndustryListByNameAndLevel(name, level);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(list);
        } catch (Exception e) {
            log.error("方法名：{}，入参：name:{}, level:{} -> exception{}", methodName, name, level, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
