package com.abcft.industrygraphmanagement.model.condition;

import com.abcft.industrygraphmanagement.model.dto.CooperationDto;
import com.abcft.industrygraphmanagement.model.dto.RelationshipEntryDto;
import lombok.Data;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/26 10:52
 * 公司-产品 生产协作图
 */
@Data
public class CooperationCondition {
    private CooperationDto cooperationDto;

    /**
     * 关系集合
     */
    private List<RelationshipEntryDto> relationshipEntryDtoList;
}
