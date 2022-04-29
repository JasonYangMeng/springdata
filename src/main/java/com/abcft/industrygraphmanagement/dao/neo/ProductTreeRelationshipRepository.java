package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.ProductTreeRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Created by YangMeng on 2021/3/23 17:55
 */
@Repository
public interface ProductTreeRelationshipRepository extends Neo4jRepository<ProductTreeRelationship, String> {

    /**
     * 判断关系是否存在
     * @param productTreeId
     * @return
     */
    @Query("match (p:ProductEntry)<-[r]-(e:ProductTreeEntry) where e.productTreeId={productTreeId}" +
            "return count(r)>0 ")
    boolean existsRelationship(String productTreeId);
}
