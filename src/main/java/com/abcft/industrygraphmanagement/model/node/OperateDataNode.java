package com.abcft.industrygraphmanagement.model.node;

import com.abcft.industrygraphmanagement.config.dbconfig.NotesValue;
import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * 公司经营数据节点
 *
 * @author WangZhiZhou
 * @date 2021/4/28
 */
@Data
@NodeEntity(label = "OperateData")
public class OperateDataNode {
    /**
     * 公司id
     */
    @Id
    private String companyEntryId;

    /**
     * 公司名称
     */
    private String name;

    /**
     * ROE（摊薄）
     */
    @NotesValue("ROE（摊薄）")
    private String dilutedRoe;

    /**
     * ROE（加权）
     */
    @NotesValue("ROE（加权）")
    private String weightedRoe;

    /**
     * 扣非后ROE（摊薄）
     */
    @NotesValue("扣非后ROE（摊薄）")
    private String cutNonrecurringDilutedRoe;

    /**
     * ROA
     */
    @NotesValue("ROA")
    private String roa;

    /**
     * ROIC
     */
    @NotesValue("ROIC")
    private String returnInvestRatio;

    /**
     * 销售毛利率
     */
    @NotesValue("销售毛利率")
    private String grossProfitMargin;

    /**
     * 销售净利润（销售利润率）
     */
    @NotesValue("销售净利润")
    private String netProfitTotRevenue;

    /**
     * EBITMARGIN(息税前利润)
     */
    @NotesValue("ebiT")
    private String ebiT;

    /**
     * EBITDAMARGIN（息税折旧摊销前利润）
     */
    @NotesValue("ebiTda")
    private String ebiTda;

    /**
     * 资产负债率
     */
    @NotesValue("资产负债率")
    private String parentAssetLiabilityRatio;

    /**
     * 资产周转率
     */
    @NotesValue("资产周转率")
    private String receivableTurnoverRatio;

    /**
     * 销售商品和劳务收到现金/营业收入
     */
    @NotesValue("销售商品和劳务收到现金/营业收入")
    private String saleGoodsLaborRevenue;

    /**
     * 截止日期
     */
    private String endDate;

}
