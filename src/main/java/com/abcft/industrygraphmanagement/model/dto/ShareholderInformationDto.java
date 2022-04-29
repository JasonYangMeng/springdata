package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * 工商数据的股东信息
 *
 * @author WangZhiZhou
 * @date 2021/4/29
 */
@Data
@QueryResult
public class ShareholderInformationDto {
    /**
     * 发起人、股东 id
     */
    private String id;

    /**
     * 发起人、股东名字
     */
    private String name;

    /**
     * 方向 不变：0  增加：1  减少：2
     */
    private String shareHoldStatus;

    /**
     * 期末参考市值（亿元）
     */
    private String termEndRefVal;

    /**
     * 持股数量（股）
     */
    private String shareHoldNum;

    /**
     * 持股数量变动（股）
     */
    private String shareHoldNumChange;

    /**
     * 占总股本比例（%）
     */
    private String proportionInTotal;

}
