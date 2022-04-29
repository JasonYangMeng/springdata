package com.abcft.industrygraphmanagement.model.entity;

import lombok.Data;

/**
 * 公司实体类
 *
 * @author WangZhiZhou
 * @date 2021/3/8
 */
@Data
public class CompanyEntity {
    /**
     * 唯一id
     */
    private String companyId;
    /**
     * 名称
     */
    private String name;
    /**
     * 注册地
     */
    private String registrationPlace;
    /**
     * 主营业务
     */
    private String mainBusiness;
    /**
     * 是否已经创建词条 1:已创建 0:未创建
     */
    private String createEntry;

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
