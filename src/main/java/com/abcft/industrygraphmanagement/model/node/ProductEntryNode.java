package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @Author Created by YangMeng on 2021/3/4 14:10
 * 产品词条
 */
@NodeEntity(label = "ProductEntry")
@Data
public class ProductEntryNode {

    @Id
    private String productEntryId;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 词条名称
     */
    private String name;

    /**
     * 词条类型  1:产品种类 2:产品类型 3:产品单元
     */
    private String type;

    /**
     * 终端产品标记   0：不是终端产品  1：是终端产品
     */
    private String terminalProductSign;

    /**
     * 市场规模
     */
    private String marketSize;

    /**
     * 毛利率
     */
    private String grossMargin;

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
     * 披露时间4
     */
    private String disclosureTime4;

    /**
     * 披露时间5
     */
    private String disclosureTime5;


    /**
     * 别名
     */
    private String aliasName;

    /**
     * 产品种类词条id
     */
    private String categoryEntryId;

    /**
     * 产品类型词条id
     */
    private String typeEntryId;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 图片路径
     */
    private String imagePath;

    /**
     * 图片名称
     */
    private String imageName;

    /**
     * 指标文件路径
     */
    private String fileTargetPath;

    /**
     * 扩展字段存储
     */
    private String extJson;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 版本号
     */
    private String version;

    /**
     * 状态 0-草稿 1-已审核通过 2-未审核通过
     */
    private String status;

    /**
     * 修改人Id
     */
    private String modifyUserId;

    /**
     * 修改时间
     */
    private String modifyTime;

    /**
     * 创建人Id
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private String createTime;
}
