package com.abcft.industrygraphmanagement.model.dto;

import com.abcft.industrygraphmanagement.model.node.PersonEntryNode;
import com.abcft.industrygraphmanagement.model.node.PersonInvestmentRelationship;
import com.abcft.industrygraphmanagement.model.node.PostRelationship;
import lombok.Data;

import java.util.List;

/**
 * 创建人物词条入参dto
 *
 * @author WangZhiZhou
 * @date 2021/3/31
 */
@Data
public class PersonEntryParamDto{
    /**
     * 人物词条节点
     */
    private PersonEntryNode personEntryNode;
    /**
     * 任职关系列表
     */
    List<PostRelationship> postList;
    /**
     * 投资关系列表
     */
    List<PersonInvestmentRelationship> investmentList;
}
