package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.PersonInvestmentRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/31
 */
public interface PersonInvestmentRelationshipRepository extends Neo4jRepository<PersonInvestmentRelationship, String> {

    /**
     * 根据人物词条id 查询该人物的所有投资关系
     *
     * @param personEntryId 人物词条id
     * @return List<PersonInvestmentRelationship>
     */
    @Query("match personInvestment = (p:PersonEntry)-[r:PersonInvestment]->(c:CompanyEntry)" +
            "where p.personEntryId = {personEntryId}" +
            "return personInvestment")
    List<PersonInvestmentRelationship> findPersonInvestmentRelationshipById(String personEntryId);
}
