package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @Author Created by YangMeng on 2021/3/24 10:13
 * 生产协作图节点
 */
@NodeEntity(label = "CooperationEntry")
@Data
public class CooperationEntryNode{
    @Id
    private String cooperationEntryId;
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
     * 图片路径
     */
    private String imagePath;

    /**
     * 图片名称
     */
    private String imageName;

    private String version;

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
