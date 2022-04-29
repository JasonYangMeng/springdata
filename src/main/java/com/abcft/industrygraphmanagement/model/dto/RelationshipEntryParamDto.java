package com.abcft.industrygraphmanagement.model.dto;

import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;
import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;
import lombok.Data;

import java.util.List;

/**
 * 创建关系词条入参dto
 *
 * @author WangZhiZhou
 * @date 2021/3/31
 */
@Data
public class RelationshipEntryParamDto {
    /**
     * 关系词条dto
     */
    private RelationshipEntryDto relationshipEntryDto;
    /**
     * 词条信息来源对象列表
     */
    private List<EntryInfoSourceEntity> entryInfoSourceEntityList;

    /**
     * 词条链接对象列表
     */
    private List<EntryLinkEntity> entryLinkEntityList;
}
