package com.abcft.industrygraphmanagement.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * 公司行情数据
 *
 * @author WangZhiZhou
 * @date 2021/4/28
 */
@Data
@NodeEntity(label = "MarketData")
public class MarketDataNode {
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
     * 交易日期
     */
    private String tradeDate;

    /**
     * 今开
     */
    private String openPrice;

    /**
     * 昨收
     */
    private String preClose;

    /**
     * 最低
     */
    private String lowPrice;

    /**
     * 最高
     */
    private String highPrice;

    /**
     * 成交量
     */
    private String transVol;

    /**
     * 流通市值
     */
    private String totalFloatValue;

    /**
     * 总市值
     */
    private String totalValue;

    /**
     * 市净率
     */
    private String pbLf;

    /**
     * PB
     */
    private String pb;

    /**
     * PE
     */
    private String pe;

    /**
     * PS
     */
    private String ps;
}
