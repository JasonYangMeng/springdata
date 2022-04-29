package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/5/7
 */
@Data
public class CompanyLineChartQuarterAndYearDto {
    /**
     * 季度折线图
     */
    private List<CompanyLineChartDto> quarterLineChartList;

    /**
     * 年度折线图
     */
    private List<CompanyLineChartDto> yearLineChartList;
}
