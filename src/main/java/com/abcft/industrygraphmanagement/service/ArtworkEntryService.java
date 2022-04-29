package com.abcft.industrygraphmanagement.service;

import com.abcft.industrygraphmanagement.model.condition.ArtworkEntryCondition;
import com.abcft.industrygraphmanagement.model.dto.ArtworkIndustryDto;
import com.abcft.industrygraphmanagement.model.dto.IndustryToCompanyDto;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/24 10:27
 */
public interface ArtworkEntryService {

    /**
     * 获取生产工艺图信息
     *
     * @param artworkEntryId
     * @return
     */
    ArtworkEntryCondition getArtworkEntryList(String artworkEntryId);

    /**
     * 编辑生产工艺图
     *
     * @param artworkEntryCondition
     * @return
     */
    boolean updateArtworkEntry(ArtworkEntryCondition artworkEntryCondition);

    /**
     * 获取生产工艺图族谱数据
     *
     * @param productEntryId
     * @return
     */
    List<IndustryToCompanyDto> getArtworkGraphList(String productEntryId, int level) throws Exception;

    /**
     * 获取工艺图产业链族谱
     */
    List<ArtworkIndustryDto> getArtworkIndustryGraph(String productEntryId,int level) throws Exception;
}
