package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.condition.PageCondition;
import com.abcft.industrygraphmanagement.model.node.NewsEntryNode;
import com.abcft.industrygraphmanagement.model.result.Paged;

/**
 * @author WangZhiZhou
 * @date 2021/4/1
 */
public interface NewsEntryService {

    /**
     * 创建新闻词条
     *
     * @param newsEntryNode 新闻词条入参dto NewsEntryParamDto
     */
    void saveNewsEntry(NewsEntryNode newsEntryNode) throws Exception;

    /**
     * 获取分页
     * @return
     */
    Paged<NewsEntryNode> getPagedList(PageCondition pageCondition);

    /**
     * 修改新闻状态
     * @param newsEntryIds
     */
    void updateNewsStatus(String[] newsEntryIds,String issue);

    /**
     * 上移下移
     * @param newsEntryId
     * @param upOrDown  1上移 0 下移
     */
    void sortById(String newsEntryId,String upOrDown) throws Exception;

    /**
     * 删除数据
     * @param newEntryIds
     */
    void deleteNode(String[] newEntryIds);

    /**
     * 新闻词条数据回显
     *
     * @param newsEntryId 新闻词条id
     * @return NewsEntryParamDto
     */
    NewsEntryNode getNewsEntryParam(String newsEntryId) throws Exception;

    /**
     * 新闻词条标题校验
     *
     * @param name 新闻标题名字
     * @return Boolean false表示标题已存在  true表示标题未创建
     */
    Boolean getNewsEntryByName(String name);
}
