package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/25 10:58
 * 生产工艺图产业链
 */
@Data
public class ArtworkIndustryDto {
    /**
     * 产业链id
     */
    private String industryEntryId;
    /**
     * 产业链名称
     */
    private String industryName;

    /**
     * 终端产品集合
     */
    List<IndustryToCompanyDto> industryToCompanyDtoList;
}
