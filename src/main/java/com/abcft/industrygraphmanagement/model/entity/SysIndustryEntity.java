package com.abcft.industrygraphmanagement.model.entity;

import lombok.Data;

import java.util.List;

/**
 * 行业表实体类
 *
 * @author WangZhiZhou
 * @date 2021/3/9
 */
@Data
public class SysIndustryEntity {
    /**
     * 唯一id UUID
     */
    private String id;
    /**
     * 父类id
     */
    private String parentId;
    /**
     * 行业等级 1:一级 2:二级 3:三级
     */
    private String level;
    /**
     * 行业编号
     */
    private String code;
    /**
     * 行业名称
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
    /**
     * 子行业列表
     */
    List<SysIndustryEntity> sysIndustryList;
}
