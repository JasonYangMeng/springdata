package com.abcft.industrygraphmanagement.model.condition;

import com.abcft.industrygraphmanagement.model.dto.ProductToProductDto;
import com.abcft.industrygraphmanagement.model.node.ArtworkEntryNode;
import lombok.Data;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/24 10:32
 */
@Data
public class ArtworkEntryCondition {

    /**
     * 生产工艺图信息
     */
    private ArtworkEntryNode artworkEntryNode;
    /**
     * 定义节点信息
     */
    List<ProductToProductDto> productToProductDtoList;
}
