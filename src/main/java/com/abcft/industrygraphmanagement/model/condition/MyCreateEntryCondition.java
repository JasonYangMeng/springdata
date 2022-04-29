package com.abcft.industrygraphmanagement.model.condition;

import lombok.Data;

/**
 * @author WangZhiZhou
 * @date 2021/5/18
 */
@Data
public class MyCreateEntryCondition extends PageCondition {
    /**
     * 模糊搜索名字
     */
    private String name;

    /**
     * 词条审核状态
     */
    private String auditStatus;

    /**
     * 词条类型
     */
    private String label;

    /**
     * 排序方式
     */
    private String order;

    /**
     * 升序，降序
     */
    private String upOrDown;
}
