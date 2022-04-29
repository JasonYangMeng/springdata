package com.abcft.industrygraphmanagement.controller;

import com.abcft.industrygraphmanagement.model.condition.PageCondition;
import com.abcft.industrygraphmanagement.model.node.NewsEntryNode;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.model.result.WebResCode;
import com.abcft.industrygraphmanagement.model.result.WebResInfo;
import com.abcft.industrygraphmanagement.service.NewsEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author WangZhiZhou
 * @date 2021/4/1
 */
@RestController
@RequestMapping("newsEntry")
@Slf4j
public class NewsEntryController {

    @Autowired
    private NewsEntryService newsEntryService;

    /**
     * 创建新闻词条
     *
     * @param newsEntryNode 新闻词条入参dto NewsEntryParamDto
     * @return WebResInfo
     */
    @PostMapping("/saveNews")
    public WebResInfo saveNewsEntry(@RequestBody NewsEntryNode newsEntryNode) {
        WebResInfo webResInfo = new WebResInfo();
        if (newsEntryNode == null) {
            webResInfo.setCode(WebResCode.Format_Parameter);
            return webResInfo;
        }
        try {
            log.info("方法名：{}，入参：{}", "saveNewsEntry", newsEntryNode);
            newsEntryService.saveNewsEntry(newsEntryNode);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setMessage("创建新闻词条成功！");
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", "saveNewsEntry", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 新闻词条数据回显
     *
     * @param newsEntryId 新闻词条id
     * @return WebResInfo
     */
    @GetMapping("/getNewsParam")
    public WebResInfo getNewsEntryParam(String newsEntryId) {
        WebResInfo webResInfo = new WebResInfo<>();
        String methodName = "getNewsEntryParam";
        if (StringUtils.isEmpty(newsEntryId)) {
            webResInfo.setCode(WebResCode.Format_Parameter);
            webResInfo.setMessage("入参不能为空！");
            return webResInfo;
        }
        try {
            log.info("方法名：{}，入参：{}", methodName, newsEntryId);
            NewsEntryNode newsEntryParam = newsEntryService.getNewsEntryParam(newsEntryId);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(newsEntryParam);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
    /**
     * 获取分页数据
     *
     * @param pageCondition
     * @return
     */
    @GetMapping("/getPagedList")
    public WebResInfo getPagedList(PageCondition pageCondition) {
        WebResInfo webResInfo = new WebResInfo();
        try {
            log.info("方法名：{}，入参：{}", "getPagedList", pageCondition);
            Paged<NewsEntryNode> pagedList = newsEntryService.getPagedList(pageCondition);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(pagedList);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", "getPagedList", e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
    /**
     * 删除数据
     *
     * @param newsEntryIds 新闻词条id
     * @return WebResInfo
     */
    @GetMapping("/deleteById")
    public WebResInfo deleteById(String[] newsEntryIds) {
        WebResInfo webResInfo = new WebResInfo<>();
        String methodName = "deleteById";
        if (StringUtils.isEmpty(newsEntryIds)) {
            webResInfo.setCode(WebResCode.Format_Parameter);
            webResInfo.setMessage("入参不能为空！");
            return webResInfo;
        }
        try {
            log.info("方法名：{}，入参：{}", methodName, newsEntryIds);
            newsEntryService.deleteNode(newsEntryIds);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 修改新闻状态
     *
     * @param newsEntryIds
     * @return WebResInfo
     */
    @GetMapping("/updateNewsStatus")
    public WebResInfo updateNewsStatus(String[] newsEntryIds, String issue) {
        WebResInfo webResInfo = new WebResInfo<>();
        String methodName = "updateNewsStatus";
        try {
            log.info("方法名：{}，入参：{}", methodName, newsEntryIds);
            newsEntryService.updateNewsStatus(newsEntryIds, issue);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 上下移动数据 1上移 0 下移
     *
     * @param newsEntryId
     * @return WebResInfo
     */
    @GetMapping("/updateOrderNum")
    public WebResInfo updateOrderNum(String newsEntryId, String upOrDown) {
        WebResInfo webResInfo = new WebResInfo<>();
        String methodName = "updateOrderNum";
        try {
            log.info("方法名：{}，入参：{}", methodName, newsEntryId);
            newsEntryService.sortById(newsEntryId, upOrDown);
            webResInfo.setCode(WebResCode.Successful);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }

    /**
     * 新闻词条标题校验
     *
     * @param title 新闻标题名字
     * @return WebResInfo
     */
    @GetMapping("/getNewsEntryByName")
    public WebResInfo getNewsEntryByName(String title) {
        WebResInfo<Boolean> webResInfo = new WebResInfo<>();
        String methodName = "getNewsEntryByName";
        if (StringUtils.isEmpty(title)) {
            webResInfo.setCode(WebResCode.Format_Parameter);
            webResInfo.setMessage("入参不能为空！");
            return webResInfo;
        }
        try {
            log.info("方法名：{}，入参：{}", methodName, title);
            Boolean result = newsEntryService.getNewsEntryByName(title);
            webResInfo.setCode(WebResCode.Successful);
            webResInfo.setData(result);
        } catch (Exception e) {
            log.error("方法名：{}，发生异常：", methodName, e);
            webResInfo.setCode(WebResCode.Server_Bug_Exception);
            webResInfo.setMessage(e.getMessage());
        }
        return webResInfo;
    }
}
