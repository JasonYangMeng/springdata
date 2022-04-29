package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * 人物词条节点
 *
 * @author WangZhiZhou
 * @date 2021/3/31
 */
@Data
@NodeEntity(label = "PersonEntry")
public class PersonEntryNode {
    /**
     * 人物词条id
     */
    @Id
    private String personEntryId;
    /**
     * 人物词条名称
     */
    private String name;
    /**
     * 模板id
     */
    private String templateId;
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
     * 图片路径
     */
    private String imagePath;
    /**
     * 图片名称
     */
    private String imageName;
    /**
     * 版本号
     */
    private String version;
    /**
     * 状态 0草稿 1已审核
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
