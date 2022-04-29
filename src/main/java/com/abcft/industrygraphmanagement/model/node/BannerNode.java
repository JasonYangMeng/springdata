package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @Author Created by YangMeng on 2021/6/15 11:46
 * 首页banner模板提示框
 */
@Data
@NodeEntity(label = "Banner")
public class BannerNode {
    /**
     * 唯一id
     */
    @Id
    private String uuid;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片路径
     */
    private String imagePath;
    /**
     * 图片名称
     */
    private String imageName;

    /**
     * 产业链id
     */
    private String industryId;

    /**
     * 序号
     */
    private int orderNum;

    /**
     * 产业链名称
     */
    private String industryName;
    /**
     * 简介
     */
    private String introduction;

    /**
     * 地址链接
     */
    private String sourceLink;

    /**
     * 发布
     */
    private String issue;

    /**
     * 运营管理类型1：banner 2: 你知道吗
     */
    private int type;

    /**
     * 修改时间
     */
    private String modifyTime;

    private String createTime;
}
