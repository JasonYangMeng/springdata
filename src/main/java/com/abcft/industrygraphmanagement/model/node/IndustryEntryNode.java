package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @Author Created by YangMeng on 2021/3/20 12:17
 * 产业链族谱词条节点
 */
@Data
@NodeEntity(value = "IndustryEntry")
public class IndustryEntryNode {
    @Id
    private String industryEntryId;

    /**
     * 名称
     */
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
     * 模板id
     */
    private String templateId;

    /**
     * 产品种类数量
     */
    private String productNum;

    /**
     * 行业
     */
    private String industry;

    /**
     * 行业名称
     */
    private String industryName;

    /**
     * 图片路径
     */
    private String imagePath;

    /**
     * 图片名称
     */
    private String imageName;

    private String modifyTime;

    private String version;

    /**
     * 市场规模
     */
    private String marketSize;

    /**
     * 表格信息
     */
    private String tableStr;

    /**
     * 披露时间1
     */
    private String disclosureTime1;

    /**
     * 披露时间2
     */
    private String disclosureTime2;

    /**
     * 披露时间3
     */
    private String disclosureTime3;

    /**
     * 创建时间
     */
    private String createTime;
}
