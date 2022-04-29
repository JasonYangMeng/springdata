package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @Author Created by YangMeng on 2021/4/8 10:44
 * 属性字典值对应表 名称-》name
 */
@Data
@NodeEntity(label = "PropertyValueDic")
public class PropertyValueDicNode {
    /**
     * 主键
     */
    @Id
    private String uuid;

    /**
     * 属性名称
     */
    private String nameEn;
    /**
     * 中文名称
     */
    private String name;
}
