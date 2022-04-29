package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.PersonEntryNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @author WangZhiZhou
 * @date 2021/3/31
 */
@Repository
public interface PersonEntryRepository extends Neo4jRepository<PersonEntryNode, String> {

}
