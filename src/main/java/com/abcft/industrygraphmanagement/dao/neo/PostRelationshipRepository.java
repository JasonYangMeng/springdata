package com.abcft.industrygraphmanagement.dao.neo;

import com.abcft.industrygraphmanagement.model.node.PostRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangZhiZhou
 * @date 2021/3/31
 */
@Repository
public interface PostRelationshipRepository extends Neo4jRepository<PostRelationship, String> {

    /**
     * 根据人物词条id 查询该人物的所有任职关系
     *
     * @param personEntryId 人物词条id
     * @return List<PostRelationship>
     */
    @Query("match postRelationship = (p:PersonEntry)-[r:Post]->(c:CompanyEntry)" +
            "where p.personEntryId = {personEntryId}" +
            "return postRelationship")
    List<PostRelationship> findPostRelationshipById(String personEntryId);
}
