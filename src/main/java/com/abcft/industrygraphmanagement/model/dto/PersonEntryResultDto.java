package com.abcft.industrygraphmanagement.model.dto;

import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;
import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;
import com.abcft.industrygraphmanagement.model.node.PersonEntryNode;
import lombok.Data;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/5/12
 */
@Data
public class PersonEntryResultDto {
    /**
     * 人物词条节点
     */
    private PersonEntryNode personEntryNode;

    /**
     * 词条信息来源对象列表
     */
    private List<EntryInfoSourceEntity> entryInfoSourceEntityList;

    /**
     * 词条链接对象列表
     */
    private List<EntryLinkEntity> entryLinkEntityList;

    /**
     * 任职关系列表
     */
    List<PersonEntryPostDto> postList;

    /**
     * 投资关系列表
     */
    List<PersonEntryInvestDto> investmentList;
}
