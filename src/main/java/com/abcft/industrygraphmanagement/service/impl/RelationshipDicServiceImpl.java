package com.abcft.industrygraphmanagement.service.impl;

import com.abcft.industrygraphmanagement.dao.neo.RelationshipDicRepository;
import com.abcft.industrygraphmanagement.model.constant.ExceptionContent;
import com.abcft.industrygraphmanagement.model.node.PropertyValueDicNode;
import com.abcft.industrygraphmanagement.model.node.RelationshipDicNode;
import com.abcft.industrygraphmanagement.model.result.Paged;
import com.abcft.industrygraphmanagement.service.RelationshipDicService;
import com.abcft.industrygraphmanagement.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/4/19
 */
@Service
public class RelationshipDicServiceImpl implements RelationshipDicService {

    @Autowired
    private RelationshipDicRepository relationshipDicRepository;

    @Override
    public void updateRelationshipDic(RelationshipDicNode node) throws Exception {
        if (node == null) {
            throw new Exception(ExceptionContent.PARAM_NULL_EXCEPTION);
        }
        if (StringUtils.isEmpty(node.getName())
                || StringUtils.isEmpty(node.getNameEn())
                || StringUtils.isEmpty(node.getDirection())) {
            throw new Exception(ExceptionContent.PARAM_REQUIRE_EXCEPTION);
        }
        // uuid为空,设置uuid
        if (StringUtils.isEmpty(node.getUuid())) {
            node.setUuid(UuidUtils.generate());
        }
        Boolean checkResult = relationshipDicRepository.checkRelationshipDicRepeat(node.getName(), node.getNameEn());
        if (!checkResult) {
            relationshipDicRepository.save(node);
        }
    }

    @Override
    public Paged<RelationshipDicNode> getRelationshipDicList(String name, Integer pageNum, Integer pageSize) {
        Paged<RelationshipDicNode> paged = new Paged<>();
        int currentNum = pageNum == 1 ? 0 : (pageNum - 1)*pageSize;
        List<RelationshipDicNode> list = relationshipDicRepository.getRelationshipDicList(name, currentNum, pageSize);
        paged.setRows(list);
        paged.setTotal(relationshipDicRepository.getTotalCount(name));

        return paged;
    }

    @Override
    public void deleteRelationshipDicList(List<String> id) {
        relationshipDicRepository.deleteRelationshipDicList(id);
    }
}
