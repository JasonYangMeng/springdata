package com.abcft.industrygraphmanagement.model.node;

import com.abcft.industrygraphmanagement.config.dbconfig.NotesValue;
import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author WangZhiZhou
 * @date 2021/4/28
 */
@Data
@NodeEntity(label = "CashFlowData")
public class CashFlowDataNode {

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
     * 销售商品提供劳务收到的现金
     */
    @NotesValue("销售商品提供劳务收到的现金")
    private String saleCash;

    /**
     * 经营活动现金净流量
     */
    @NotesValue("经营活动现金净流量")
    private String businessCashTotal;

    /**
     * 购建固定无形长期资产支付的现金
     */
    @NotesValue("购建固定无形长期资产支付的现金")
    private String subsPayCash;

    /**
     * 投资支付的现金
     */
    @NotesValue("投资支付的现金")
    private String investPayCash;

    /**
     * 投资活动现金流量
     */
    @NotesValue("投资活动现金流量")
    private String investCashNetValue;

    /**
     * 筹资活动现金净流量
     */
    @NotesValue("筹资活动现金净流量")
    private String borrowCashNetValue;

    /**
     * 折旧与摊销
     */
    @NotesValue("折旧与摊销")
    private String depRecAmortization;

    /**
     * 取得借款收到的现金
     */
    @NotesValue("取得借款收到的现金")
    private String recBorrowCash;

    /**
     * 现金净增加额
     */
    @NotesValue("现金净增加额")
    private String cashToNetAdd;

    /**
     * 期末现金余额
     */
    @NotesValue("期末现金余额")
    private String lastCash;

    /**
     * 吸收投资收到的现金
     */
    @NotesValue("吸收投资收到的现金")
    private String recInvestRecCash;

    /**
     * 截止日期
     */
    private String endDate;

}
