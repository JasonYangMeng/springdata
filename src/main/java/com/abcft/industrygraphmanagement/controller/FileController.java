package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author Created by YangMeng on 2021/3/8 13:54
 */
@RestController
@RequestMapping("file")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传文件到指定服务器
     *
     * @param request
     * @return
     */
    @PostMapping(value = "uploadFile")
    public WebResInfo uploadFile(MultipartHttpServletRequest request) {
        MultipartFile multipartFile = request.getFile("file");
        log.info("开始上传文件:{}", multipartFile.getOriginalFilename());
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            String filePath = fileService.uploadFile(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
            if (StringUtils.isEmpty(filePath)) {
                webResInfo.setCode(WebResCode.Server_Bug_Exception);
            }
            webResInfo.setData(filePath);
        } catch (Exception e) {
            log.error("uploadFile error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }


    /**
     * 下载文件到指定目录
     *
     * @return
     */
    @GetMapping(value = "downloadFile")
    public WebResInfo downloadFile(String sourcePath, String targetPath) {
        log.info("开始下载文件:{}");
        WebResInfo webResInfo = new WebResInfo();
        try {
            webResInfo.setCode(WebResCode.Successful);
            boolean downloadFile = fileService.downloadFile(sourcePath, targetPath);
            webResInfo.setData(downloadFile);
        } catch (Exception e) {
            log.error("downloadFile error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 获取图片
     *
     * @param filePath
     * @param response
     */
    @GetMapping(value = "/getFile/{filePath}")
    public WebResInfo getFile(@PathVariable("filePath") String filePath, HttpServletResponse response) {
        log.info("getFile:{}", filePath);
        WebResInfo webResInfo = new WebResInfo();
        if (StringUtils.isEmpty(filePath)) {
            return WebResInfo.parameterStruct;
        }
        try {
            filePath = "/" + filePath.replaceAll("_", "/");
            webResInfo.setCode(WebResCode.Successful);
            fileService.getFile(filePath, response);
        } catch (Exception e) {
            log.error("getFile error:{}", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
