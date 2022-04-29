package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;

/**
 * @Author Created by YangMeng on 2021/3/29 13:42
 */
@Data
public class CooperationDto {

    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 公司id
     */
    private String companyEntryId;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品id
     */
    private String productEntryId;

    private String name;

    /**
     * 别名
     */
    private String aliasName;

    /**
     * 简介
     */
    private String introduction;
    /**
     * 图片路径
     */
    private String imagePath;
}
