package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @Author Created by YangMeng on 2021/7/5 11:10
 * 数据源
 */
@NodeEntity(label = "DataSource")
@Data
public class DataSourceNode {

    @Id
    private String uuid;

    /**
     * 名称
     */
    private String name;

    /**
     * 图标
     */
    private String imagePath;

    /**
     * 数据模板 1，2，3
     */
    private String dataTemplate;

    /**
     * 数据模版对应的type
     */
    private int type;

    /**
     * 创建时间
     */
    private String createTime;

}
