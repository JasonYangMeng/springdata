package com.abcft.industrygraphmanagement.model.condition;

import lombok.Data;

/**
 * @Author Created by YangMeng on 2021/5/12 13:58
 */
@Data
public class SysCollectCondition extends PageCondition {

    private String userId;

    /**
     * 名称
     */
    private String name;
    /**
     * 类别
     */
    private String typeName;
}
