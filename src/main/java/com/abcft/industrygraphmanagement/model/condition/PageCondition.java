package com.abcft.industrygraphmanagement.model.condition;

import lombok.Data;

/**
 * @Author Created by YangMeng on 2021/5/12 14:11
 */
@Data
public class PageCondition {
    /**
     * 每页条数
     */
    private int size;
    /**
     * 页码
     */
    private int page;

    /**
     * 是否上架1：上架 0：下架
     */
    private String issue = "";
    /**
     * 类型
     */
    private int type;

    /**
     * 名称查询
     */
    private String name = "";
}
