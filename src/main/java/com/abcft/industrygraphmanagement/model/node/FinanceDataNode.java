package com.abcft.industrygraphmanagement.model.node;

import com.abcft.industrygraphmanagement.config.dbconfig.NotesValue;
import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * 财务数据
 *
 * @author WangZhiZhou
 * @date 2021/4/28
 */
@Data
@NodeEntity(label = "FinanceData")
public class FinanceDataNode {
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
     * 营业总收入
     */
    @NotesValue("营业总收入")
    private String totalOperatingRevenue;

    /**
     * 营业总成本
     */
    @NotesValue("营业总成本")
    private String overallCost;

    /**
     * 营业利润
     */
    @NotesValue("营业利润")
    private String operatingProfit;

    /**
     * 利润总额
     */
    @NotesValue("利润总额")
    private String totalProfit;

    /**
     * 归属母公司股东的净利润
     */
    @NotesValue("归属母公司股东的净利润")
    private String parentNp;

    /**
     * 非经常性损益
     */
    @NotesValue("非经常性损益")
    private String totalNonRecurProfit;

    /**
     * 扣非后归属母公司股东的净利润
     */
    @NotesValue("扣非后归属母公司股东的净利润")
    private String cutNonrecurringParentNp;

    /**
     * 研发支出
     */
    @NotesValue("研发支出")
    private String rdExpenses;

    /**
     * 截止日期
     */
    private String endDate;

}
