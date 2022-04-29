package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.node.PropertyValueDicNode;
import com.abcft.industrygraphmanagement.model.result.Paged;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/4/19
 */
public interface PropertyValueDicService {

    /**
     * 属性值字典维护
     *
     * @param node 节点
     */
    void updatePropertyValueDic(PropertyValueDicNode node) throws Exception;

    /**
     * 模糊查询属性值字典 分页
     *
     * @param name 名字
     * @return List<PropertyValueDicNode>
     */
    Paged<PropertyValueDicNode> getPropertyValueDicList(String name, int pageNum, int pageSize);

    void deletePropertyValueDicList(List<String> id);
}
