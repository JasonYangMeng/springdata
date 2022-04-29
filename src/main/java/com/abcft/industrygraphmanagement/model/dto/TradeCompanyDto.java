package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;

/**
 * @author WangZhiZhou
 * @date 2021/4/6
 */
@Data
public class TradeCompanyDto {
    /**
     * 公司id
     */
    private String companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司所属行业,级联行业 如：机械设备/通用机械/机床工具
     */
    private String cascadeTrade;
}
