package com.abcft.industrygraphmanagement.model.constant;

/**
 * @Author Created by YangMeng on 2021/3/18 20:00
 * 关系类型
 */
public interface RelationshipType {
    /**
     * 公司-公司 供给关系
     */
    String SUPPLY = "Supply";

    /**
     * 公司->公司  投资关系
     */
    String INVESTMENT = "Investment";

    /**
     * 公司->公司  经销关系
     */
    String DISTRIBUTION = "Distribution";

    /**
     * 人物-公司 任职关系
     */
    String POST = "Post";

    /**
     * 人物->公司  投资关系
     */
    String PERSON_INVESTMENT = "PersonInvestment";

    /**
     * 公司-产品 生产关系
     */
    String PRODUCTION = "Production";

    /**
     * 产品种类-产品类型，产品类型-产品单元 归属关系
     */
    String ASCRIPTION = "Ascription";

    /**
     * 产品-产品 原材料关系
     */
    String MATERIALS = "Materials";

    /**
     * 产品-产品 制造设备关系
     */
    String MANUFACTURE = "Manufacture";

    /**
     * 产品-产品 加工工艺关系
     */
    String TECHNOLOGY = "Technology";

    /**
     * 产业链-产品 产业链关系
     */
    String INDUSTRY = "Industry";

    /**
     * 族谱-产品 产品族谱关系
     */
    String PRODUCTTREE = "ProductTree";

    /**
     * 工艺图-产品 生产工艺图关系
     */
    String ARTWORK = "Artwork";

    /**
     * 协作图-产品 生产协作图关系
     */
    String COOPERATION = "Cooperation";
}
