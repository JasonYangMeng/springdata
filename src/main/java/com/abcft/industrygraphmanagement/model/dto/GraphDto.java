package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/18 10:39
 * 公司产业链图谱展示
 */
@Data
public class GraphDto {
    /**
     * 公司词条id
     */
    private String companyEntryId;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 产品词条id
     */
    private String productEntryId;
    /**
     * 产品名称
     */
    private String productName;
    private List<GraphDto> parentGraphList;
    private List<GraphDto> childGraphList;
}
