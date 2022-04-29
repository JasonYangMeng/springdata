package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.ProductTreeEntryNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Created by YangMeng on 2021/3/23 17:54
 */
@Repository
public interface ProductTreeEntryRepository extends Neo4jRepository<ProductTreeEntryNode, String> {
    /**
     * 删除归属关系
     *
     * @param productEntryId
     */
    @Query("match re=(p:ProductEntry)-[r:Ascription*1..]->(:ProductEntry) " +
            "where p.productEntryId={productEntryId} foreach(n in relationships(re)|delete n)")
    void deleteProductRelation(String productEntryId);

}
