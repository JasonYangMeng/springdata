package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.CooperationRelationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Created by YangMeng on 2021/3/24 10:26
 */
@Repository
public interface CooperationRelationshipRepository extends Neo4jRepository<CooperationRelationship, String> {
}
