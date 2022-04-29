package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @Author Created by YangMeng on 2021/6/17 10:43
 * 热搜
 */
@Data
@NodeEntity(label = "TopSearch")
public class TopSearchNode {

    @Id
    private String uuid;
    /**
     * 名称
     */
    private String name;
    /**
     * 关键词
     */
    private String keyword;

    /**
     * 序号
     */
    private int orderNum;

    /**
     * 发布
     */
    private String issue;
    /**
     * 运营管理类型1： 2:
     */
    private int type;

    /**
     * 修改时间
     */
    private String modifyTime;

    /**
     * 创建时间
     */
    private String createTime;
}
