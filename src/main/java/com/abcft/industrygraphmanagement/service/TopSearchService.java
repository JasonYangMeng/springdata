package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.condition.PageCondition;
import com.abcft.industrygraphmanagement.model.node.TopSearchNode;
import com.abcft.industrygraphmanagement.model.result.Paged;

/**
 * @Author Created by YangMeng on 2021/6/17 10:49
 */
public interface TopSearchService {
    /**
     * 创建Banner
     *
     * @param topSearchNode
     */
    void saveNode(TopSearchNode topSearchNode) throws Exception;

    /**
     * 获取分页
     * @return
     */
    Paged<TopSearchNode> getPagedList(PageCondition pageCondition);
    /**
     * 修改状态
     *
     * @param uuids
     */
    void updateStatus(String[] uuids, String issue);

    /**
     * 删除数据
     * @param uuids
     */
    void deleteNode(String[] uuids);

    /**
     * 上移下移
     * @param uuid
     * @param upOrDown  1上移 0 下移
     */
    void sortById(String uuid,String upOrDown) throws Exception;

    /**
     * 词条数据回显
     *
     * @param uuid
     * @return TopSearchNode
     */
    TopSearchNode getEntryById(String uuid) throws Exception;
}
