package com.abcft.industrygraphmanagement.model.entity;

import lombok.Data;

/**
 * @Author Created by YangMeng on 2021/5/12 11:54
 */
@Data
public class SysSubscribeSettingEntity {
    private String id;
    private String name;
    private String userId;
    private String entryId;
    private String typeName;
    private String remind;
    private String createTime;
}
