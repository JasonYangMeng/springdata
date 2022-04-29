package com.abcft.industrygraphmanagement.model.entity;

import lombok.Data;

/**
 * @Author Created by YangMeng on 2021/3/3 11:18
 */
@Data
public class SysDictionaryEntity {

    private String id;
    /**
     * 父级id
     */
    private String parentId;
    /**
     * 类型
     */
    private String type;
    /**
     * 等级
     */
    private String level;
    /**
     * 编码
     */
    private String code;
    /**
     * 值
     */
    private String value;
    /**
     * 创建人id
     */
    private String createUserId;
    /**
     * 创建时间
     */
    private String createTime;
}
