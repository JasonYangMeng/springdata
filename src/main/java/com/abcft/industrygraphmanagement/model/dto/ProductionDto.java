package com.abcft.industrygraphmanagement.model.dto;


import lombok.Data;

/**
 * @Author Created by YangMeng on 2021/7/28 10:38
 */
@Data
public class ProductionDto {
    /**
     * 公司id
     */
    private String companyId;
    /**
     * 产品id
     */
    private String productId;

    private String productName;


    public ProductionDto(String companyId, String productId) {
        this.companyId = companyId;
        this.productId = productId;
    }


    public ProductionDto(String companyId, String productId,String productName) {
        this.companyId = companyId;
        this.productId = productId;
        this.productName=productName;
    }
}
