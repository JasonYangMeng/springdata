package com.abcft.industrygraphmanagement.model.entity;

import lombok.Data;

/**
 * 人物实体类
 * 属性名和人物词条节点的属性名一致，
 * 但和mysql人物表的字段名不一致，所以sql语句要使用别名
 *
 * @author WangZhiZhou
 * @date 2021/4/1
 */
@Data
public class PersonEntity {
    /**
     * 人物唯一id
     */
    private String personEntryId;
    /**
     * 人物词条名称
     */
    private String name;
    /**
     * 公司词条id
     */
    private String companyEntryId;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 任职职务
     */
    private String post;
    /**
     * 简介
     */
    private String introduction;
    /**
     * 是否已经创建词条
     */
    private String createEntry;
    /**
     * 图片路径
     */
    private String imagePath;
    /**
     * 创建人Id
     */
    private String createUserId;
    /**
     * 创建时间
     */
    private String createTime;
}
