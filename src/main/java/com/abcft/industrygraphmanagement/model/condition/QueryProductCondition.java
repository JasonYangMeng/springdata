package com.abcft.industrygraphmanagement.model.condition;

import lombok.Data;

/**
 * 查询产品词条入参请求信息
 * @author WangZhiZhou
 * @date 2021/3/15
 */
@Data
public class QueryProductCondition {
    /**
     * 产品词条id
     */
    private String productEntryId;

    /**
     * 1：产品种类 2：产品类型 3：产品单元
     */
    private String type;
}
