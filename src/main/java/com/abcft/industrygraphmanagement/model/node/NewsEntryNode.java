package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * 新闻词条节点
 *
 * @author WangZhiZhou
 * @date 2021/4/1
 */
@Data
@NodeEntity(label = "NewsEntry")
public class NewsEntryNode {
    /**
     * 新闻词条id
     */
    @Id
    private String newsEntryId;

    /**
     * 词条标题
     */
    private String title;

    /**
     * 查看原文链接
     */
    private String sourceLink;

    /**
     * 摘要
     */
    private String abstracts;

    /**
     * 序号
     */
    private int orderNum;

    /**
     * 图片路径
     */
    private String imagePath;
    /**
     * 图片名称
     */
    private String imageName;

    /**
     * 发布
     */
    private String issue;

    /**
     * 运营管理类型1： 2:
     */
    private int type;

    /**
     * 上架时间
     */
    private String modifyTime;

    /**
     * 创建时间
     */
    private String createTime;
}
