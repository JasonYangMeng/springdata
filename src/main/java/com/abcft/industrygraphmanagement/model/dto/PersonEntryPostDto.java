package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;

/**
 * @author WangZhiZhou
 * @date 2021/5/12
 */
@Data
public class PersonEntryPostDto {

    /**
     * 公司id
     */
    private String id;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 职务
     */
    private String post;

    /**
     * 持股比例 / 股权比例
     */
    private String shareholdingRatio;
}
