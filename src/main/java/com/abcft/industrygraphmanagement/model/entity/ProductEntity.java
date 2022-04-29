package com.abcft.industrygraphmanagement.model.entity;

import lombok.Data;

/**
 * 产品词条实体类
 *
 * @author WangZhiZhou
 * @date 2021/3/9
 */
@Data
public class ProductEntity {
    /**
     * 唯一id
     */
    private String productId;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 名称
     */
    private String productName;

    /**
     * 是否已经创建词条 1:已创建 0:未创建
     */
    private String createEntry;

    /**
     * 产业链创建状态 1:已创建 0:未创建
     */
    private String industryStatus;
    /**
     * 产品族谱创建状态 1:已创建 0:未创建
     */
    private String productTreeStatus;
    /**
     * 生产工艺图建状态 1:已创建 0:未创建
     */
    private String artworkStatus;

    /**
     * 产品区分类型  1：产品种类 2：产品类型 3：产品单元
     */
    private String type;

    /**
     * 产品审核状态 0：待审核   1：审核通过 2: 审核未通过
     */
    private String auditStatus;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private String createTime;
}
