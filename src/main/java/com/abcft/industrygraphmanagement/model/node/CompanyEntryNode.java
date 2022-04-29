package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

/**
 * @Author Created by YangMeng on 2021/3/3 14:40
 */
@NodeEntity(label = "CompanyEntry")
@Data
public class CompanyEntryNode {

    @Id
    private String companyEntryId;
    /**
     * 模板id
     */
    private String templateId;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;

    /**
     * 别名
     */
    private String aliasName;
    /**
     * 行业
     */
    private String industry;

    /**
     * 行业名称
     */
    private String industryName;


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
     * 经营范围
     */
    private String scope;

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
     * 文件名称
     */
    private String fileName;

    /**
     * 扩展字段存储
     */
    private String extJson;

    /**
     * 版本号
     */
    private String version;

    /**
     * 产品备注
     */
    private String productRemark;

    /**
     * 状态: 0-草稿 1-已审核 2-未通过
     */
    private String status;

    /**
     * 修改人Id
     */
    private String modifyUserId;

    /**
     * 股票代码
     */
    private String tickerSymbol;

    /**
     * 股票简称
     */
    private String stkName;

    /**
     * 公司英文名称
     */
    private String comNameEn;

    /**
     * 公司英文简称
     */
    private String comSnameEn;

    /**
     * 公司国籍
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;
    /**
     * 统一社会信用代码
     */
    private String uscCode;
    /**
     * 注册资本（万元）
     */
    private String regAss;
    /**
     * 货币名称
     */
    private String curName;
    /**
     * 注册地址
     */
    private String registrationPlace;
    /**
     * 办公地址
     */
    private String offAdd;
    /**
     * 邮编
     */
    private String zipCode;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 联系传真
     */
    private String fax;
    /**
     * 电子信箱
     */
    private String email;
    /**
     * 公司网址
     */
    private String web;
    /**
     * 主营业务
     */
    private String mainBusiness;
    /**
     * 成立日期
     */
    private String estDate;

    /**
     * 公司终止日期
     */
    private String comEndDate;
    /**
     * 公司类型
     */
    private String comType;

    /**
     * 上市版块
     */
    private String ipoSector;

    /**
     * 公司类型1：1-其他公司，2-金融公司
     */
    private String cominfoType;
    /**
     * 企业性质
     */
    private String busNature;
    /**
     * 公司简介
     */
    private String businessIntroduction;

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
