package com.abcft.industrygraphmanagement.model.dto;

import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;
import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;
import com.abcft.industrygraphmanagement.model.entity.ProductGenealogyEntity;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import lombok.Data;

import java.util.List;

/**
 * 产品词条 数据回显 Dto
 *
 * @author WangZhiZhou
 * @date 2021/3/19
 */
@Data
public class ProductEntryResultDto extends EntryInfoSettingDto {
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
     * 产品种类节点关系对象列表
     */
    private List<NodeRelationDto> proCategoryRelList;
    /**
     * 产品类型节点关系对象列表
     */
    private List<NodeRelationDto> proTypeRelList;
    /**
     * 产品单元节点关系对象列表
     */
    private List<NodeRelationDto> proUnitRelList;
    /**
     * 产品族谱
     */
    private ProductGenealogyEntity proGenealogy;
    /**
     * 上游产品节点关系对象列表
     */
    private List<NodeRelationDto> proUpStreamRelList;
    /**
     * 下游产品节点关系对象列表
     */
    private List<NodeRelationDto> proDownStreamRelList;
    /**
     * 生产商节点关系对象列表
     */
    private List<NodeRelationDto> proCompanyRelList;

}
