package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;

/**
 * @Author Created by YangMeng on 2021/5/13 11:41
 */
@Data
public class EntryInfoSettingDto {

    /**
     * 是否订阅
     */
    private boolean subscribe;
    /**
     * 是否收藏
     */
    private boolean collect;
    /**
     * 是否点赞
     */
    private boolean like;
    /**
     * 点赞数量
     */
    private int likeNum;
}
