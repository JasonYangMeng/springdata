package com.abcft.industrygraphmanagement.model.dto;

import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;
import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import lombok.Data;

import java.util.List;

/**
 * 添加产品词条、编辑产品词条入参对象
 * ProductEntryParamDto
 *
 * @author WangZhiZhou
 * @date 2021/3/21
 */
@Data
public class ProductEntryParamDto {
    /**
     * 当前创建的产品词条对象
     */
    private ProductEntryNode currentNode;
    /**
     * 词条信息来源对象列表
     */
    private List<EntryInfoSourceEntity> entryInfoSourceEntityList;

    /**
     * 词条链接对象列表
     */
    private List<EntryLinkEntity> entryLinkEntityList;
    /**
     * ProductToProductDto列表
     */
    private List<ProductToProductDto> productToProductDtoList;
}
