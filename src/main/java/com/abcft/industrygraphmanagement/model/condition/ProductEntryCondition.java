package com.abcft.industrygraphmanagement.model.condition;

import com.abcft.industrygraphmanagement.model.entity.EntryInfoSourceEntity;
import com.abcft.industrygraphmanagement.model.entity.EntryLinkEntity;
import com.abcft.industrygraphmanagement.model.node.AscriptionRelationship;
import com.abcft.industrygraphmanagement.model.node.ProductEntryNode;
import lombok.Data;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/11
 */
@Data
public class ProductEntryCondition {
    /**
     * 产品词条节点
     */
    private ProductEntryNode productEntryNode;

    /**
     * 词条信息来源
     */
    private List<EntryInfoSourceEntity> entryInfoSourceEntityList;

    /**
     * 词条链接
     */
    private List<EntryLinkEntity> entryLinkEntityList;

    /**
     * 产品-产品  归属关系 1-n
     */
    private List<AscriptionRelationship> ascriptionRelationship;
}
