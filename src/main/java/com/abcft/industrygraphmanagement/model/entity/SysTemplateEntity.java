package com.abcft.industrygraphmanagement.model.entity;

import lombok.Data;

/**
 * @Author Created by YangMeng on 2021/3/9 16:02
 */
@Data
public class SysTemplateEntity {
    private String id;
    /**
     * 模版类型
     */
    private String type;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板内容
     */
    private String content;
    private String createUserId;
    private String createTime;
}
