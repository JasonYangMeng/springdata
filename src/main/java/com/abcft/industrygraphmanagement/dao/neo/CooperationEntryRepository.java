package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.CooperationEntryNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Created by YangMeng on 2021/3/24 10:25
 */
@Repository
public interface CooperationEntryRepository extends Neo4jRepository<CooperationEntryNode, String> {
}
