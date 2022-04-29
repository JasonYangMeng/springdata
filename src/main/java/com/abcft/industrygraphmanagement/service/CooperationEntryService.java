package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.condition.CooperationCondition;
import com.abcft.industrygraphmanagement.model.dto.GraphDto;
import com.abcft.industrygraphmanagement.model.dto.RelationshipEntryDto;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/24 10:28
 */
public interface CooperationEntryService {

    /**
     * 获取生产协作图信息
     *
     * @param companyEntryId
     * @param productEntryId
     * @return
     */
    CooperationCondition getCooperationEntryList(String companyEntryId, String productEntryId);

    /**
     * 获取生产协作图列表
     *
     * @param companyEntryId
     * @param productEntryId
     * @param relationshipEntryDtoList
     */
    void getRelationshipDtoList(String companyEntryId, String productEntryId, List<RelationshipEntryDto> relationshipEntryDtoList);

    /**
     *
     * @param companyEntryId
     * @param productEntryId
     * @return
     */
    GraphDto getCooperationGraph(String companyEntryId, String productEntryId);

    /**
     * 编辑生产工艺图
     *
     * @param cooperationCondition
     * @return
     */
    boolean updateCooperation(CooperationCondition cooperationCondition) throws Exception;
}
