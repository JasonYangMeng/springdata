package com.abcft.industrygraphmanagement.model.condition;

import com.abcft.industrygraphmanagement.model.dto.ProductToProductDto;
import com.abcft.industrygraphmanagement.model.dto.EntryInfoSettingDto;
import com.abcft.industrygraphmanagement.model.node.IndustryEntryNode;
import lombok.Data;

import java.util.List;

/**
 * @Author Created by YangMeng on 2021/3/20 14:50
 */
@Data
public class IndustryEntryCondition extends EntryInfoSettingDto {
    /**
     * 产业链图谱信息
     */
    private IndustryEntryNode industryEntryNode;
    /**
     * 定义节点信息
     */
    List<ProductToProductDto> productToProductDtoList;
}
