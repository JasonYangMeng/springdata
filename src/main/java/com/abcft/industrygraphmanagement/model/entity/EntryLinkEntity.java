package com.abcft.industrygraphmanagement.model.entity;

import lombok.Data;

/**
 * @Author Created by YangMeng on 2021/3/9 17:09
 * 词条链接表
 */
@Data
public class EntryLinkEntity {

    private String id;

    /**
     * 词条id
     */
    private String entryId;
    /**
     * 词条类型
     */
    private String type;
    /**
     * 词条属性key
     */
    private String key;
    /**
     * 词条属性值
     */
    private String value;
    /**
     * 链接地址
     */
    private String link;
    /**
     * 链接类型 内部0 外部1
     */
    private String linkType;
    /**
     * 链接文字
     */
    private String linkWord;
    private String createUserId;
    private String createTime;
}
