package com.abcft.industrygraphmanagement.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/12 13:44
 */
@Data
public class ProductToCompanyDto {
    private String productEntryId;
    private List<SupplyParentDto> supplyParentDtoList;
}
