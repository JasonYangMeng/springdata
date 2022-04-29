package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.CashFlowDataNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Created by YangMeng on 2021/7/29 15:37
 */
@Repository
public interface CashFlowDataRepository extends Neo4jRepository<CashFlowDataNode, String> {

}
