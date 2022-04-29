package com.abcft.industrygraphmanagement.model.entity;

import lombok.Data;

/**
 * @Author Created by YangMeng on 2021/3/9 17:06
 * 词条信息来源表
 */
@Data
public class EntryInfoSourceEntity {
    private String entryInfoSourceId;
    private String entryId;
    /**
     * 词条类型 公司 产品 关系
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
     * 资料
     */
    private String filePath;

    /**
     * 文件名称
     */
    private String fileName;

    private String createUserId;
    private String createTime;
}
