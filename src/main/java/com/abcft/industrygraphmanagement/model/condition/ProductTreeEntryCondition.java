package com.abcft.industrygraphmanagement.model.condition;

import com.abcft.industrygraphmanagement.model.dto.ProductToProductDto;
import com.abcft.industrygraphmanagement.model.node.ProductTreeEntryNode;
import lombok.Data;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/23 18:00
 */
@Data
public class ProductTreeEntryCondition {
    /**
     * 产品图谱信息
     */
    private ProductTreeEntryNode productTreeEntryNode;
    /**
     * 定义节点信息
     */
    List<ProductToProductDto> productToProductDtoList;
}
