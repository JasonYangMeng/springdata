package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.condition.PageCondition;
import com.abcft.industrygraphmanagement.model.node.DataSourceNode;
import com.abcft.industrygraphmanagement.model.result.Paged;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/7/5 11:22
 */
public interface DataSourceService {
    /**
     * 创建
     *
     * @param dataSourceNode
     */
    void saveNode(DataSourceNode dataSourceNode);

    /**
     * 获取分页
     *
     * @return
     */
    Paged<DataSourceNode> getPagedList(PageCondition pageCondition);

    /**
     * 获取所有数据源
     *
     * @return
     */
    List<DataSourceNode> getAllList();

    /**
     * 删除数据
     *
     * @param uuids
     */
    void deleteNode(String[] uuids);
}
