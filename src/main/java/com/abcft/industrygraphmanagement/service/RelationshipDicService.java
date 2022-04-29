package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.node.PropertyValueDicNode;
import com.abcft.industrygraphmanagement.model.node.RelationshipDicNode;
import com.abcft.industrygraphmanagement.model.result.Paged;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/4/19
 */
public interface RelationshipDicService {

    void updateRelationshipDic(RelationshipDicNode node) throws Exception;


    Paged<RelationshipDicNode> getRelationshipDicList(String name, Integer pageNum, Integer pageSize);

    void deleteRelationshipDicList(List<String> id);
}
