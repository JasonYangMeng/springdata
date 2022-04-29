package com.abcft.industrygraphmanagement.model.node;

import com.abcft.industrygraphmanagement.config.dbconfig.NotesValue;
import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * 公司资产负债节点
 *
 * @author WangZhiZhou
 * @date 2021/4/28
 */
@Data
@NodeEntity(label = "AssetsLiabilities")
public class AssetsLiabilitiesNode {

    /**
     * 公司id
     */
    @Id
    private String companyEntryId;

    /**
     * 公司名字
     */
    private String name;

    /**
     * 流动资产
     */
    @NotesValue("流动资产")
    private String totalCurrentAsset;

    /**
     * 固定资产
     */
    @NotesValue("固定资产")
    private String totFixAsset;

    /**
     * 长期股权投资
     */
    @NotesValue("长期股权投资")
    private String longEquityInvestment;

    /**
     * 资产总计
     */
    @NotesValue("资产总计")
    private String totalAsset;

    /**
     * 流动负债
     */
    @NotesValue("流动负债")
    private String totalCurrentLiabilities;

    /**
     * 非流动负债
     */
    @NotesValue("非流动负债")
    private String totalNonCurrentLiabilities;

    /**
     * 负债合计
     */
    @NotesValue("负债合计")
    private String totalLiabilities;

    /**
     * 股东权益
     */
    @NotesValue("股东权益")
    private String totalAccountEquity;

    /**
     * 归属母公司股东权益
     */
    @NotesValue("归属母公司股东权益")
    private String totalAccountParentEquity;

    /**
     * 截止日期
     */
    private String endDate;

}
