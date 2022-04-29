package com.abcft.industrygraphmanagement.model.condition;

import lombok.Data;

/**
 * @Author Created by YangMeng on 2021/6/18 14:00
 */
@Data
public class BannerCondition {
    /**
     * 类型
     */
    private int type;

    /**
     * 是否上架1：上架 0：下架
     */
    private String issue = "";
    /**
     * 每页条数
     */
    private int size;
    /**
     * 页码
     */
    private int page;
}
