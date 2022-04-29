package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;

/**
 * @author WangZhiZhou
 * @date 2021/5/12
 */
@Data
public class PersonEntryInvestDto {

    /**
     * 公司id
     */
    private String id;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 投资占比
     */
    private String investmentProportion;
}
