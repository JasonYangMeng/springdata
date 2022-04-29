package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.IndustryRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @Author Created by YangMeng on 2021/3/22 11:26
 * 产业链关系
 */
public interface IndustryRelationshipRepository extends Neo4jRepository<IndustryRelationship, String> {

    /**
     * 判断关系是否存在
     * @param industryEntryId
     * @return
     */
    @Query("match (p:ProductEntry)-[r]->(e:IndustryEntry) where e.industryEntryId={industryEntryId}" +
            "return count(r)>0 ")
    boolean existsRelationship(String industryEntryId);
}
