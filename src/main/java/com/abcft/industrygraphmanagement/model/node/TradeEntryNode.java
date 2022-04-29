package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * 行业词条节点
 *
 * @author WangZhiZhou
 * @date 2021/4/2
 */
@Data
@NodeEntity(label = "TradeEntry")
public class TradeEntryNode {
    /**
     * 行业词条id
     */
    @Id
    private String tradeEntryId;
    /**
     * 行业词条名称
     */
    private String name;
    /**
     * 模板id
     */
    private String templateId;
    /**
     * 行业级别
     */
    private String level;
    /**
     * 父级行业id
     */
    private String parentTradeId;
    /**
     * 行业简介
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
     * 状态
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
